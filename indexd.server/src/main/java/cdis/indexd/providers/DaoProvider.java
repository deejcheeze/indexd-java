package cdis.indexd.providers;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

public class DaoProvider {
	
	@PersistenceUnit(unitName = "nurlng")
	private EntityManagerFactory em;
	
	@PostConstruct
	public void init() {
		
	}

}
