package cdis.indexd.values;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@XmlRootElement(name="id_list")
@XmlAccessorType(XmlAccessType.FIELD)
public class IndexParams {
	
	private List<String> ids;
	private int limit = 100;
	private int size;
	private int start;
	
	@JsonProperty("file_name")
	private String fileName;
	
	private String[] urls;
	private String version;
	
	private String[] hashes;
	private String[] metadata;

}
