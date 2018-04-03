package tr.com.mse.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

import tr.com.mse.annotation.FieldMatch;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

	private String firstFieldName;
	private String secondFieldName;

	@Override
	public void initialize(final FieldMatch constraintAnnotation) {
		firstFieldName = constraintAnnotation.firstField();
		secondFieldName = constraintAnnotation.secondField();
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		try {
			BeanWrapperImpl objectWrapper = new BeanWrapperImpl(value);
			final Object firstObj = objectWrapper.getPropertyValue(firstFieldName);
			final Object secondObj = objectWrapper.getPropertyValue(secondFieldName);
			if (firstObj == null || secondObj == null)
				return true;

			if (!(firstObj != null && firstObj.equals(secondObj))) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(secondFieldName).addConstraintViolation();
				return false;
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}

		return true;
	}
}