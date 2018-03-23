package cdis.indexd.hibernate.dialect;

import java.sql.Types;

import org.hibernate.dialect.H2Dialect;

public class IndexH2Dialect extends H2Dialect {
	
	public IndexH2Dialect() {
		this.registerColumnType(Types.JAVA_OBJECT, "text");
	}

}
