package cdis.indexd.model;

import java.io.Serializable;

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

}
