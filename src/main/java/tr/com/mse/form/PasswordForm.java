package tr.com.mse.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import tr.com.mse.annotation.FieldMatch;

@FieldMatch(firstField = "newPassword", secondField = "passwordConfirm", message = "{match.password}")
public class PasswordForm {
	
	@NotEmpty(message = "{notempty.user.password}")
	@Size(min = 6, message = "{size.user.password}")
	private String currentPassword;

	@NotEmpty(message = "{notempty.user.password}")
	@Size(min = 6, message = "{size.user.password}")
	private String newPassword;

	@NotEmpty(message = "{notempty.user.password}")
	@Size(min = 6, message = "{size.user.password}")
	private String passwordConfirm;

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
}