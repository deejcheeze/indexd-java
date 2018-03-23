package cdis.indexd.model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import cdis.indexd.converters.JsonListConverter;
import cdis.indexd.converters.JsonMapConverter;

public class JsonConverterTest {
	
	private static JsonMapConverter mapConverter;
	private static JsonListConverter converter;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		converter = new JsonListConverter();
		mapConverter = new JsonMapConverter();
	}
	
	@Test
	public void testMapConversion() {
		Map<String, Object> md = new HashMap<>();
		md.put("dummy_key", "12");
		md.put("dummy_bool", Boolean.TRUE);
		md.put("dummy_obj", new FileHash());
		
		String json = mapConverter.convertToDatabaseColumn(md);
		assertEquals("{\"dummy_bool\":true,\"dummy_obj\":{\"md5\":null,\"sha1\":null,\"sha256\":null,\"sha512\":null,\"crc\":null,\"etag\":null},\"dummy_key\":\"12\"}", json);
	}
	
	@Test
	public void testMapCreation() {
		Map<String, Object> md = mapConverter.convertToEntityAttribute("{\"dummy_bool\":true,\"dummy_obj\":{\"md5\":null,\"sha1\":null,\"sha256\":null,\"sha512\":null,\"crc\":null,\"etag\":null},\"dummy_key\":\"12\"}");
		assertTrue((Boolean)md.get("dummy_bool"));
	}

	@Test
	public void testListConversion() {
		String cvt = converter.convertToDatabaseColumn((List<String>) Arrays.asList("a", "b"));
		assertEquals(cvt, "[\"a\",\"b\"]");
	}
	
	@Test
	public void testListCreation() {
		List<String> cvt = converter.convertToEntityAttribute("[\"a\",\"b\"]");
		assertEquals(cvt, Arrays.asList("a", "b"));
	}

}
