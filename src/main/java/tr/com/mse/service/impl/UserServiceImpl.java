package tr.com.mse.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tr.com.mse.enums.AuthorityType;
import tr.com.mse.form.UserForm;
import tr.com.mse.mapper.UserMapper;
import tr.com.mse.model.Authority;
import tr.com.mse.model.User;
import tr.com.mse.repository.AuthorityRepository;
import tr.com.mse.repository.UserRepository;
import tr.com.mse.service.UserLogService;
import tr.com.mse.service.UserService;

@Service("UserService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	AuthorityRepository authorityRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserMapper userMapper;

	@Autowired
	UserLogService userLogService;

	@Override
	public User findByObjid(String objid) {
		return userRepo.findOne(objid);
	}

	@Override
	public UserForm getUserFormByObjid(String objid) {
		User userInfo = findByObjid(objid);
		return userMapper.mapToForm(userInfo);
	}

	@Override
	public User findByUserCode(String userCode) {
		return userRepo.findByUserCode(userCode);
	}

	@Override
	public UserForm getUserForm(String userCode) {
		User userInfo = findByUserCode(userCode);
		return userMapper.mapToForm(userInfo);
	}

	@Override
	public UserForm getUserForm(User userInfo) {
		return userMapper.mapToForm(userInfo);
	}

	@Override
	public List<UserForm> getAllUsers() {
		List<User> userList = userRepo.findAll();
		List<UserForm> userFormList = new ArrayList<UserForm>();
		for (User userInfo : userList)
			userFormList.add(userMapper.mapToForm(userInfo));

		return userFormList;
	}

	@Override
	@Transactional
	public void saveUser(UserForm userForm) {
		User userInfo = userMapper.mapToEntity(userForm);
		Set<Authority> authoritySet = new HashSet<>();
		authoritySet.add(authorityRepo.findByName(AuthorityType.USER));
		if (userForm.isAdmin())
			authoritySet.add(authorityRepo.findByName(AuthorityType.ADMIN));

		userInfo.setAuthorities(authoritySet);
		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));

		userRepo.saveAndFlush(userInfo);
		userLogService.addUserCreatedLog(userInfo);
	}

	@Override
	@Transactional
	public UserForm updateUser(User userInfo, UserForm userForm) {
		try {
			User currentUserInfo = userInfo.clone();

			userInfo.setUserName(userForm.getUserName());
			userInfo.setUserSurname(userForm.getUserSurname());
			userInfo.setEmail(userForm.getEmail());
			if (userForm.getObjid() != null) {
				Set<Authority> authoritySet = new HashSet<>();
				authoritySet.add(authorityRepo.findByName(AuthorityType.USER));
				if (userForm.isAdmin())
					authoritySet.add(authorityRepo.findByName(AuthorityType.ADMIN));

				userInfo.setAuthorities(authoritySet);
			}

			userRepo.saveAndFlush(userInfo);
			userLogService.addUserUpdateLog(currentUserInfo, userForm);

			return userMapper.mapToForm(userInfo);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return new UserForm();
	}

	@Override
	@Transactional
	public void resetPassword(User userInfo, String newPassword) {
		userInfo.setPassword(passwordEncoder.encode(newPassword));
		userInfo.setLastPasswordChange(new Date());
		userRepo.saveAndFlush(userInfo);
		userLogService.addUserResetPasswordLog(userInfo);
		resetFailedLogin(userInfo.getUserCode());
	}

	@Override
	public void incrementFailedLogin(String userCode) {
		User userInfo = findByUserCode(userCode);
		if (userInfo != null) {
			userInfo.setFailedLoginCount(userInfo.getFailedLoginCount() + 1);
			userRepo.saveAndFlush(userInfo);
		}
	}

	@Override
	public void resetFailedLogin(String userCode) {
		User userInfo = findByUserCode(userCode);
		userInfo.setFailedLoginCount(0);

		userRepo.saveAndFlush(userInfo);
	}

	@Override
	@Transactional
	public void switchUserStatus(String objid) {
		User userInfo = findByObjid(objid);
		userInfo.setEnabled(!userInfo.isEnabled());

		userRepo.saveAndFlush(userInfo);
		if (userInfo.isEnabled())
			userLogService.addUserEnabledLog(userInfo);
		else
			userLogService.addUserDisabledLog(userInfo);
	}

	@Override
	public void deleteUser(String objid) {
		User userInfo = findByObjid(objid);
		userRepo.delete(userInfo);
	}
}