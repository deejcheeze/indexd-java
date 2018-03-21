package cdis.indexd.constraints;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.BeforeClass;
import org.junit.Test;

public class IndexHashTest {
	
	private static Validator val;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		val = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	public void testNullHashes() {
		Document doc = new Document(null);
		Set<ConstraintViolation<Document>> violations = val.validate(doc);
		assertEquals(1, violations.size());
	}
	
	@Test
	public void testInvalidHashes() {
		
		Map<String, String> hashes = new HashMap<>();
		hashes.put("XG", "sss");
		
		Document doc = new Document(hashes);
		Set<ConstraintViolation<Document>> violations = val.validate(doc);
		assertEquals(1, violations.size());
	}
	
	class Document {
		
		@IndexHash
		Map<String, String> hashes;
		
		public Document(Map<String, String> hashes) {
			this.hashes = hashes;
		}
	}

}
