package cdis.indexd.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import cdis.indexd.annotations.IndexHash;
import cdis.indexd.enums.IndexHashType;
import cdis.indexd.model.FileHash;

public class FileHashValidator implements ConstraintValidator<IndexHash, FileHash> {
	
	@Override
	public boolean isValid(FileHash value, ConstraintValidatorContext ctx) {
		
		if(value == null) {
			return false;
		}
		
		int hashes = 0;
		String hash = value.getMd5();
		if(hash != null && IndexHashType.MD5.isValid(hash)) {
			hashes += 1;
		}
		
		hash = value.getSha1();
		if(hash != null && IndexHashType.SHA1.isValid(hash)) {
			hashes += 1;
		}
		
		hash = value.getSha256();
		if(hash != null && IndexHashType.SHA256.isValid(hash)) {
			hashes += 1;
		}
		
		hash = value.getSha512();
		if(hash != null && IndexHashType.SHA512.isValid(hash)) {
			hashes += 1;
		}
		
		hash = value.getCrc();
		if(hash != null && IndexHashType.CRC.isValid(hash)) {
			hashes += 1;
		}
		
		hash = value.getEtag();
		if(hash != null && IndexHashType.ETAG.isValid(hash)) {
			hashes += 1;
		}
		
		if(hashes == 0) {
			return false;
		}
		
		return true;
	}
	
}
