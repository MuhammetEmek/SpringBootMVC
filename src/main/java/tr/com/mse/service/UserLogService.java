package tr.com.mse.service;

import tr.com.mse.form.UserForm;
import tr.com.mse.model.User;

public interface UserLogService {

	void addUserCreatedLog(User userInfo);

	void addUserEnabledLog(User userInfo);

	void addUserDisabledLog(User userInfo);

	void addUserResetPasswordLog(User userInfo);

	void addUserChangePasswordLog(User userInfo);

	void addUserUpdateLog(User userInfo, UserForm updatedUser);
}