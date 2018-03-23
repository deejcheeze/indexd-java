package cdis.indexd.model;

import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

import cdis.indexd.annotations.FileIndexHash;
import cdis.indexd.annotations.IndexID;
import lombok.Getter;
import lombok.Setter;
import nw.orm.core.IEntity;

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
	
	@Transient
	private String baseId;
	
	@JsonProperty("file_name")
	@Column(nullable = true)
	private String fileName;
	
	@Column(nullable = true)
	private String version;
	
	@JsonProperty("size")
	@Column(nullable = true)
	private BigInteger fileSize;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "base_index_fk", nullable = false, updatable = false)
	private BaseIndex baseIndex;
	
	@FileIndexHash
	@Type(type = "FileHashType")
	private FileHash hashes;
	
	@Type(type = "UrlType")
	@JsonProperty("urls")
	private FileUrl urls;
	
	@JsonProperty("metadata")
	@Type(type = "MetaDataType")
	private MetaData metaData;
	
	public String getBaseId() {
		if(baseIndex != null) {
			return baseIndex.getDid();
		}
		return null;
	}

}
