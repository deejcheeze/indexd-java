package cdis.indexd.values;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import cdis.indexd.annotations.IndexHash;
import cdis.indexd.annotations.IndexID;

@XmlRootElement(name="document")
@XmlAccessorType(XmlAccessType.FIELD)
public class Document {
	
	private int size;
	
	@IndexID
	private String did;
	
	@IndexHash
	private Map<String, String> hashes;
	
	private Map<String, Object> metadata;
	
	@JsonProperty("file_name")
	@SerializedName("file_name")
	private String fileName = "aaa.aaa";
	
	@JsonProperty("urls")
	private List<@URL String> urls;
	
	public Document(String did, String[] urls, String fileName, int size, Map<String, String> hashes, Map<String, Object> metadata) {
		this.did = did;
		this.size = size;
		this.addUrls(urls);
		this.hashes = hashes;
		this.metadata = metadata;
		this.fileName = fileName;
	}
	
	public String getDid() {
		return did;
	}
	
	public int getSize() {
		return size;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	private void addUrls(String[] urls) {
		if(this.urls == null) {
			this.urls = new ArrayList<>();
		}
		
		this.urls.addAll(Arrays.asList(urls));
	}
	
	public Map<String, String> getHashes() {
		return this.hashes;
	}
	
	public String[] getUrls() {
		return this.urls.toArray(new String[0]);
	}
	
	public static void main(String[] args) {
		Validator val = Validation.buildDefaultValidatorFactory().getValidator();
		Document document = new Document("AAAgoogle.com", new String[] {"http://www.google.com"}, "dalu.txt", 34, new HashMap<>(), new HashMap<>());
		System.out.println(val.validate(document));
		
		Gson gs = new Gson();
		String json = gs.toJson(document);
		System.out.println(json);
		
		Document fj = gs.fromJson("{\"did\":\"AAAgoogle.com\",\"file_name\":\"daalu.txt\",\"urls\":[\"http://www.google.com\"]}", Document.class);
		System.out.println(fj.fileName);
	}
	
}
