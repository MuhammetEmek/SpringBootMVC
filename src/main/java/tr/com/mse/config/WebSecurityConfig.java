package tr.com.mse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import tr.com.mse.enums.AuthorityType;
import tr.com.mse.security.AuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	AuthenticationProvider authenticationProvider;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
		        .csrf().disable()

		        .authorizeRequests()
		        .antMatchers("/resources/**", "/js/**", "/image/**", "/public/**").permitAll()
		        .and()

		        .authorizeRequests()
		        .antMatchers("/users/**").hasAnyAuthority(AuthorityType.USER.toString(), AuthorityType.ADMIN.toString())
		        .antMatchers("/admin/**").hasAuthority(AuthorityType.ADMIN.toString())
		        .anyRequest().authenticated()
		        .and()

		        .formLogin()
		        .loginPage("/login")
		        .permitAll()
		        .and()

		        .logout()
		        .permitAll();
	}

	public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}
}