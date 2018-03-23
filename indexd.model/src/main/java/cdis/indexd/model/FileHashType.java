package cdis.indexd.model;

import cdis.indexd.hibernate.types.JsonType;

public class FileHashType extends JsonType {

	@Override
	public Class<FileHash> returnedClass() {
		return FileHash.class;
	}

}
