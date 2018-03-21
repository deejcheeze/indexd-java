package cdis.indexd.validators;

import java.util.Map;
import java.util.Map.Entry;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import cdis.indexd.annotations.IndexHash;
import cdis.indexd.enums.IndexHashType;

public class IndexHashValidator implements ConstraintValidator<IndexHash, Map<String, String>> {
	
	@Override
	public boolean isValid(Map<String, String> value, ConstraintValidatorContext ctx) {
		
		if(value == null) {
			return false;
		}
		
		for(Entry<String, String> key: value.entrySet()) {
			if(!validate(key, ctx)) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean validate(Entry<String, String> entry, ConstraintValidatorContext ctx) {
		
		String hashType = entry.getKey();
		IndexHashType indexHashType = IndexHashType.fromString(hashType);
		if(indexHashType == null) {
			return false;
		}
		
		return indexHashType.isValid(entry.getValue());
	}
	
}
