package cdis.indexd.converters;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapConverter implements AttributeConverter<Map<String, Object>, String> {

	@Override
	public String convertToDatabaseColumn(Map<String, Object> attribute) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final StringWriter w = new StringWriter();
		try {
			mapper.writeValue(w, attribute);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		w.flush();
		return w.toString();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> convertToEntityAttribute(String dbData) {
		final ObjectMapper mapper = new ObjectMapper();
		
		try {
			return mapper.readValue(dbData, Map.class);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	} 

}
