package cdis.indexd.hibernate.types;

import cdis.indexd.model.MetaData;

public class MetaDataType extends JsonType {

	@Override
	public Class<MetaData> returnedClass() {
		return MetaData.class;
	}

}
