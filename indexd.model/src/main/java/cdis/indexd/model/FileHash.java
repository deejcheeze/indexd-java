package cdis.indexd.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class FileHash implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8196430671130456795L;

	@JsonProperty("md5")
	private String md5;
	
	@JsonProperty("sha1")
	private String sha1;
	
	@JsonProperty("sha256")
	private String sha256;
	
	@JsonProperty("sha512")
	private String sha512;
	
	@JsonProperty("crc")
	private String crc;
	
	@JsonProperty("etag")
	private String etag;
	
	public Map<String, String> toMap() {
		Map<String, String> hashesMap = new HashMap<>();
		hashesMap.put("md5", this.getMd5());
		hashesMap.put("sha1", this.getSha1());
		hashesMap.put("sha256", this.getSha256());
		hashesMap.put("sha512", this.getSha512());
		hashesMap.put("crc", this.getCrc());
		hashesMap.put("etag", this.getEtag());
		return hashesMap;
	}
	
	public static FileHash fromMap(Map<String, String> hashesMap) {
		
		FileHash hash = new FileHash();
		hash.setMd5(hashesMap.get("md5"));
		hash.setSha1(hashesMap.get("sha1"));
		hash.setSha256(hashesMap.get("sha256"));
		hash.setSha512(hashesMap.get("sha512"));
		hash.setCrc(hashesMap.get("crc"));
		hash.setEtag(hashesMap.get("etag"));
		return hash;
	}

}
