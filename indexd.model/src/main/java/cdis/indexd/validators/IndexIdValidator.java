package cdis.indexd.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import cdis.indexd.annotations.IndexID;

public class IndexIdValidator implements ConstraintValidator<IndexID, String>{
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null;
	}

}
