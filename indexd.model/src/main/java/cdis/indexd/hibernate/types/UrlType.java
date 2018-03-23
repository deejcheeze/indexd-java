package cdis.indexd.hibernate.types;

import cdis.indexd.model.FileUrl;

public class UrlType extends JsonType {

	@Override
	public Class<FileUrl> returnedClass() {
		return FileUrl.class;
	}

}
