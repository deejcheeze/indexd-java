package cdis.indexd.converters;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Handles converting lisy of strings to json object
 * during persistence
 * @author Rowland O. Ogwara
 *
 */
public class JsonListConverter implements AttributeConverter<List<String>, String> {

	@Override
	public String convertToDatabaseColumn(List<String> attribute) {
		
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
	public List<String> convertToEntityAttribute(String dbData) {
		final ObjectMapper mapper = new ObjectMapper();
		
		try {
			return mapper.readValue(dbData, List.class);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}


}
