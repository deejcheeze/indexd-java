package cdis.indexd.values;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import cdis.indexd.annotations.IndexHash;
import lombok.Data;

@Data
@XmlRootElement(name="document")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Document {
	
	private int size;
	private String rev;
	private String did;
	
	@JsonProperty("baseid")
	private String baseId;
	private String version;
	private String form = "object";
	
	@JsonProperty("created_date")
	private Date createdDate;
	
	@JsonProperty("updated_date")
	private Date updatedDate;
	
	@IndexHash
	private Map<String, String> hashes;
	
	private Map<String, Object> metadata;
	
	@JsonProperty("file_name")
	private String fileName;
	
	@JsonProperty("urls")
	private List<@URL String> urls;
	
	public Document() {
		this(null, new String[0], null, 0, new HashMap<>(), new HashMap<>());
	}
	
	public Document(String did, String[] urls, String fileName, int size, Map<String, String> hashes, Map<String, Object> metadata) {
		this.did = did;
		this.size = size;
		this.addUrls(urls);
		this.hashes = hashes;
		this.metadata = metadata;
		this.fileName = fileName;
	}
	
	private void addUrls(String[] urls) {
		if(this.urls == null) {
			this.urls = new ArrayList<>();
		}
		
		this.urls.addAll(Arrays.asList(urls));
	}
	
}
