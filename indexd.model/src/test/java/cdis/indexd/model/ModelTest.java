package cdis.indexd.model;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

public class ModelTest {
	
	private static EntityManagerFactory em;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		em = Persistence.createEntityManagerFactory("indexd");
	}
	
	@Test
	public void testConnect() {
		EntityManager mgr = em.createEntityManager();
		mgr.getTransaction().begin();
		BaseIndex bi = new BaseIndex();
		bi.setDid("AAAA");
		
		mgr.persist(bi);
		
		mgr.getTransaction().commit();
		
		assertNotNull(bi.getPk());
		
	}

}
