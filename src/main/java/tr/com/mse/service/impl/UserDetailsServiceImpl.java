package tr.com.mse.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tr.com.mse.form.UserForm;
import tr.com.mse.model.Authority;
import tr.com.mse.model.User;
import tr.com.mse.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUserCode(username);
		if (user == null)
			throw new UsernameNotFoundException("User not found");
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Authority role : user.getAuthorities())
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName().toString()));

		UserForm userForm = userService.getUserForm(user);
		
		return new org.springframework.security.core.userdetails.User(
		        user.getUserCode(),
		        user.getPassword(),
		        user.isEnabled(),
		        true,
		        !userForm.isPasswordExpired(),
		        !userForm.isAccountLocked(),
		        grantedAuthorities);
	}
}