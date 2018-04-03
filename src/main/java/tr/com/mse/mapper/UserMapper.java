package tr.com.mse.mapper;

import java.util.Date;

import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import tr.com.mse.enums.AuthorityType;
import tr.com.mse.form.UserForm;
import tr.com.mse.model.User;
import tr.com.mse.repository.AuthorityRepository;

public class UserMapper {

	@Autowired
	AuthorityRepository authorityRepo;
	
	@Transient
	@Value("${security.password.expired.date}")
	private int passwordExpiredDateCount;

	@Transient
	@Value("${security.password.max.failed.count}")
	private int maxFailedLoginCount;

	public User mapToEntity(UserForm form) {
		User user = new User();
		user.setObjid(form.getObjid());
		user.setUserCode(form.getUserCode());
		user.setUserName(form.getUserName());
		user.setUserSurname(form.getUserSurname());
		user.setEmail(form.getEmail());
		user.setPassword(form.getPassword());

		return user;
	}

	public UserForm mapToForm(User user) {
		UserForm userForm = new UserForm();
		userForm.setObjid(user.getObjid());
		userForm.setUserCode(user.getUserCode());
		userForm.setUserName(user.getUserName());
		userForm.setUserSurname(user.getUserSurname());
		userForm.setEmail(user.getEmail());
		userForm.setPassword(user.getPassword());
		userForm.setEnabled(user.isEnabled());
		userForm.setAdmin(user.getAuthorities().contains(authorityRepo.findByName(AuthorityType.ADMIN)));
		userForm.setAccountLocked(user.getFailedLoginCount() >= maxFailedLoginCount);
		if (user.getLastPasswordChange() != null)
			userForm.setPasswordExpired((new Date().getTime() - user.getLastPasswordChange().getTime()) / (24 * 60 * 60 * 1000) > passwordExpiredDateCount);
		else
			userForm.setPasswordExpired(false);

		return userForm;
	}
}