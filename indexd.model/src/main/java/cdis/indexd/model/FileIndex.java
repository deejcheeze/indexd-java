package cdis.indexd.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonProperty;

import cdis.indexd.annotations.FileIndexHash;
import cdis.indexd.annotations.IndexID;
import cdis.indexd.converters.JsonListConverter;
import cdis.indexd.converters.JsonMapConverter;
import cdis.indexd.hibernate.types.FileHashType;
import lombok.Getter;
import lombok.Setter;
import nw.orm.core.IEntity;

@TypeDefs({
	@TypeDef(name = "FileHashType", typeClass = FileHashType.class),
})
@Getter @Setter
@Entity @Table(name = "file_indexes")
@XmlRootElement(name = "index")
@XmlAccessorType(XmlAccessType.FIELD)
public class FileIndex extends IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3982934621479520069L;
	
	@IndexID 
	@Column(unique = true)
	private String did;
	
	private String rev;
	private String form;
	
	@JsonProperty("file_name")
	@Column(nullable = true, name = "file_name")
	private String fileName;
	
	@Column(nullable = true, name="version")
	private String version;
	
	@JsonProperty("size")
	@Column(nullable = true, name = "file_size")
	private BigInteger fileSize;
	
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "base_index_fk", nullable = false, updatable = false)
	private BaseIndex baseIndex;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_fk", nullable = false, updatable = false)
	private User createdBy;
	
	@FileIndexHash
	@Type(type = "FileHashType")
	private FileHash hashes;
	
	@JsonProperty("urls") @Valid
	@Convert(converter = JsonListConverter.class)
	@Column(name = "urls", nullable = true)
	private List<@URL String> urls;
	
	@JsonProperty("metadata") @Valid
	@Convert(converter = JsonMapConverter.class)
	@Column(name = "meta_data", nullable = true)
	private Map<String, Object> metaData;
	
	public String getBaseId() {
		if(baseIndex != null) {
			return baseIndex.getDid();
		}
		return null;
	}
	
	public void addUrl(String url) {
		if(urls == null) {
			urls = new ArrayList<>();
		}
		urls.add(url);
	}
	
	public void addMetaData(String key, Object val) {
		
		if (metaData == null) {
			metaData = new HashMap<>();
		}
		metaData.put(key, val);
	}

	public void freshDid() {
		this.did = UUID.randomUUID().toString();
	}

}
