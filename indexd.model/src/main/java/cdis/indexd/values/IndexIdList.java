package cdis.indexd.values;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import cdis.indexd.annotations.IndexHash;
import lombok.Data;

@Data
@XmlRootElement(name="id_list")
@XmlAccessorType(XmlAccessType.FIELD)
public class IndexIdList {
	
	private List<String> ids;
	private int limit;
	private int size;
	private int start;
	
	@JsonProperty("file_name")
	private String fileName;
	
	private List<String> urls;
	private String version;
	
	@IndexHash
	private Map<String, String> hashes;
	
	private Map<String, Object> metadata;

}
