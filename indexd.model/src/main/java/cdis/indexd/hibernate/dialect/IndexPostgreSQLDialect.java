package cdis.indexd.hibernate.dialect;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL9Dialect;

public class IndexPostgreSQLDialect extends PostgreSQL9Dialect{
	
	public IndexPostgreSQLDialect() {
		this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
		this.registerColumnType(Types.CLOB, "jsonb");
	}

}
