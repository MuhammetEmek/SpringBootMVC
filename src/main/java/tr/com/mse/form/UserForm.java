package tr.com.mse.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import tr.com.mse.annotation.FieldMatch;

@FieldMatch(firstField = "password", secondField = "passwordConfirm", message = "{match.password}")
public class UserForm {
	
	private String objid;

	@NotEmpty(message = "{notempty.user.name}")
	private String userCode;

	private String userName;

	private String userSurname;

	private String email;

	@NotEmpty(message = "{notempty.user.password}")
	@Size(min = 6, message = "{size.user.password}")
	private String password;

	@NotEmpty(message = "{notempty.user.password}")
	@Size(min = 6, message = "{size.user.password}")
	private String passwordConfirm;

	private boolean admin;

	private boolean enabled;

	private boolean accountLocked;

	private boolean passwordExpired;

	public String getObjid() {
		return objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSurname() {
		return userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public boolean isPasswordExpired() {
		return passwordExpired;
	}

	public void setPasswordExpired(boolean passwordExpired) {
		this.passwordExpired = passwordExpired;
	}
}