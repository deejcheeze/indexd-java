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
	
	@Column(nullable = true)
	private String fileName;

}
