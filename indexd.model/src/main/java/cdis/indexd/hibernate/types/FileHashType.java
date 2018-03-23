package cdis.indexd.hibernate.types;

import cdis.indexd.model.FileHash;

public class FileHashType extends JsonType {

	@Override
	public Class<FileHash> returnedClass() {
		return FileHash.class;
	}

}
