package tr.com.mse.security;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import tr.com.mse.service.UserService;

@Component
public class AuthenticationProvider extends DaoAuthenticationProvider {


	@Autowired
	@Qualifier("validationMessageSource")
	ResourceBundleMessageSource validationMessageSource;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserService userService;

	@Override
	@Autowired
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		super.setPasswordEncoder(passwordEncoder);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			Authentication auth = super.authenticate(authentication);

			userService.resetFailedLogin(authentication.getName());
			return auth;
		}
		catch (org.springframework.security.authentication.DisabledException e) {
			throw new LockedException(getValidationMessage("security.exception.account.disabled"));
		}
		catch (LockedException e) {
			throw new LockedException(getValidationMessage("security.exception.account.lock"));
		}
		catch (CredentialsExpiredException e) {
			throw new LockedException(getValidationMessage("security.exception.account.expired"));
		}
		catch (BadCredentialsException e) {
			userService.incrementFailedLogin(authentication.getName());
			throw new BadCredentialsException(getValidationMessage("security.exception.bad.credential"));
		}
		catch (Exception e) {
			throw new LockedException(getValidationMessage("security.exception.bad.credential"));
		}
	}

	private String getValidationMessage(String messageKey) {
		return validationMessageSource.getMessage(messageKey, null, Locale.getDefault());
	}
}