package tr.com.mse.service;

import java.util.List;

import tr.com.mse.form.UserForm;
import tr.com.mse.model.User;

public interface UserService {

	User findByObjid(String objid);

	User findByUserCode(String userCode);

	UserForm getUserForm(String userCode);

	UserForm getUserForm(User userInfo);

	UserForm getUserFormByObjid(String objid);

	List<UserForm> getAllUsers();

	void saveUser(UserForm userForm);

	UserForm updateUser(User userInfo, UserForm userForm);

	void resetPassword(User userInfo, String newPassword);

	void incrementFailedLogin(String userCode);

	void resetFailedLogin(String userCode);

	void switchUserStatus(String objid);

	void deleteUser(String objid);

}