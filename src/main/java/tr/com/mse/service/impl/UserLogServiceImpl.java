package tr.com.mse.service.impl;

import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import tr.com.mse.enums.UserOperation;
import tr.com.mse.form.UserForm;
import tr.com.mse.model.User;
import tr.com.mse.model.UserLog;
import tr.com.mse.repository.UserLogRepository;
import tr.com.mse.service.SecurityService;
import tr.com.mse.service.UserLogService;
import tr.com.mse.service.UserService;

@Service
public class UserLogServiceImpl implements UserLogService {

	@Autowired
	UserService userService;

	@Autowired
	UserLogRepository userLogRepo;
	
	@Autowired
	SecurityService securityService;

	@Autowired
	@Qualifier("messageSource")
	ResourceBundleMessageSource messageSource;

	@Override
	public void addUserCreatedLog(User userInfo) {
		addUserLog(userInfo, UserOperation.CREATE);
	}

	@Override
	public void addUserEnabledLog(User userInfo) {
		addUserLog(userInfo, UserOperation.MAKE_ENABLE);
	}

	@Override
	public void addUserDisabledLog(User userInfo) {
		addUserLog(userInfo, UserOperation.MAKE_DISABLE);
	}

	@Override
	public void addUserResetPasswordLog(User userInfo) {
		addUserLog(userInfo, UserOperation.RESET_PASSWORD);
	}

	@Override
	public void addUserChangePasswordLog(User userInfo) {
		addUserLog(userInfo, UserOperation.CHANGE_PASSWORD);
	}

	@Override
	public void addUserUpdateLog(User userInfo, UserForm updatedUser) {
		if (!userInfo.getUserCode().equals(updatedUser.getUserCode()))
			addUserUpdateLog(userInfo, getMessage("user.userCode"), userInfo.getUserCode(), updatedUser.getUserCode());
		
		if(!userInfo.getUserName().equals(updatedUser.getUserName()))
			addUserUpdateLog(userInfo, getMessage("user.userName"), userInfo.getUserName(), updatedUser.getUserName());
		
		if(!userInfo.getUserSurname().equals(updatedUser.getUserSurname()))
			addUserUpdateLog(userInfo, getMessage("user.userSurname"), userInfo.getUserSurname(), updatedUser.getUserSurname());
		
		if(!userInfo.getEmail().equals(updatedUser.getEmail()))
			addUserUpdateLog(userInfo, getMessage("user.email"), userInfo.getEmail(), updatedUser.getEmail());
	}

	private void addUserUpdateLog(User userInfo, String fieldName, String oldValue, String newValue) {
		UserLog userLog = new UserLog();
		userLog.setUserInfo(userInfo);
		userLog.setFieldName(fieldName);
		userLog.setOldValue(oldValue);
		userLog.setNewValue(newValue);
		userLog.setOperationDate(new Date());
		userLog.setOperation(UserOperation.UPDATE);
		userLog.setOperationUser(userService.findByUserCode(securityService.getLoggedInUserCode()));

		userLogRepo.saveAndFlush(userLog);
	}

	private void addUserLog(User userInfo, UserOperation operation) {
		UserLog userLog = new UserLog();
		userLog.setUserInfo(userInfo);
		userLog.setOperationDate(new Date());
		userLog.setOperation(operation);
		userLog.setOperationUser(userService.findByUserCode(securityService.getLoggedInUserCode()));

		userLogRepo.saveAndFlush(userLog);
	}

	private String getMessage(String messageKey) {
		return messageSource.getMessage(messageKey, null, Locale.getDefault());
	}

}