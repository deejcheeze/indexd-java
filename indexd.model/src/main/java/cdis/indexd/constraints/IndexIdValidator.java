package cdis.indexd.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IndexIdValidator implements ConstraintValidator<IndexID, String>{
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null;
	}

}
