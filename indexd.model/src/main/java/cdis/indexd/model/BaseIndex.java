package cdis.indexd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import cdis.indexd.annotations.IndexID;
import lombok.Getter;
import lombok.Setter;
import nw.orm.core.IEntity;


@Getter @Setter
@Entity @Table(name = "base_indexes")
@XmlRootElement(name = "base")
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseIndex extends IEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5065950668315691737L;
	
	@IndexID 
	@Column(unique = true, updatable = false, nullable = false)
	private String did;
	
	@Column(nullable = false)
	private String activeVersion;
	

}
