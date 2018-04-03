package tr.com.mse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import tr.com.mse.mapper.UserMapper;

@SpringBootApplication
public class MVCApplication {

	static final Logger LOG = LoggerFactory.getLogger(MVCApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MVCApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserMapper userMapper() {
		return new UserMapper();
	}

	@Bean
	@Qualifier("messageSource")
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasenames("i18n/messages");
		source.setDefaultEncoding("UTF-8");
		source.setUseCodeAsDefaultMessage(true);

		return source;
	}

	@Bean
	@Qualifier("validationMessageSource")
	public ResourceBundleMessageSource validationMessageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasenames("i18n/validation");
		source.setDefaultEncoding("UTF-8");
		source.setUseCodeAsDefaultMessage(true);

		return source;
	}

	@Bean
	public javax.validation.Validator validator() {
		final LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
		factory.setValidationMessageSource(validationMessageSource());
		return factory;
	}
}