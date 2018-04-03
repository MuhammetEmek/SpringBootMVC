package tr.com.mse.service;

public interface SecurityService {

	public String getLoggedInUserCode();

	public void checkCurrentPassword(String userCode, String password);
}