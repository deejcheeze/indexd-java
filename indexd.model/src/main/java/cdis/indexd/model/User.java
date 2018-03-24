package cdis.indexd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cdis.indexd.enums.Role;
import lombok.Getter;
import lombok.Setter;
import nw.orm.core.IEntity;

@Getter @Setter
@Entity @Table(name="users")
@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User extends IEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2223331644160047737L;

	@Size(min = 4, max = 64)
	@Column(unique = true, length = 64, nullable = false)
	private String username;
	
	@JsonIgnore
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role = Role.user;
	
	private boolean verified;
	private boolean blacklisted;

}
