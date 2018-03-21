package cdis.indexd.constraints;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.BeforeClass;
import org.junit.Test;

import cdis.indexd.annotations.IndexID;

public class IndexIdTest {
	
	private static Validator val;

	@BeforeClass
	public static void setUp() throws Exception {
		val = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	public void testNullIndexId() {
		
		Document doc = new Document(null);
		Set<ConstraintViolation<Document>> violations = val.validate(doc);
		assertEquals(1, violations.size());
		assertEquals("Blank values are not allowed", violations.iterator().next().getMessage());
	}
	
	@Test
	public void testInvalidUUID() {
		
		Document doc = new Document("AAA");
		Set<ConstraintViolation<Document>> violations = val.validate(doc);
		assertEquals(1, violations.size());
		assertEquals("must be a valid UUID4 string", violations.iterator().next().getMessage());
	}
	
	@Test
	public void testValid() {
		
		Document doc = new Document("47536a9e-89fb-4175-a494-261789081738");
		Set<ConstraintViolation<Document>> violations = val.validate(doc);
		assertEquals(0, violations.size());
	}
	
	class Document {
		
		@IndexID
		String did;
		
		public Document(String did) {
			this.did = did;
		}
	}

}
