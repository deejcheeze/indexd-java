package cdis.indexd.model;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ModelEntityTest {
	
	private static EntityManagerFactory em;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		em = Persistence.createEntityManagerFactory("indexd-test");
	}
	
	@Test
	public void testStructure() throws JsonProcessingException {

		FileIndex fi = new FileIndex();
		fi.setForm("aaaa");
		
		FileHash has = new FileHash();
		has.setEtag("AAAAAAAAAAAAAAAAAAAAAAA");
		fi.setHashes(has);
		
		fi.addUrl("ggogle.com");
		fi.addMetaData("aws", "none");
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(fi));
}
	
	@Test
	public void testConnect() {
		EntityManager mgr = em.createEntityManager();
		mgr.getTransaction().begin();
		BaseIndex bi = new BaseIndex();
		bi.setActiveVersion("1.0");
		mgr.persist(bi);
		
		mgr.getTransaction().commit();
		
		assertNotNull(bi.getPk());
		
	}

}
