package cdis.indexd.values;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import cdis.indexd.annotations.IndexID;
import lombok.Data;

@Data
@XmlRootElement(name="did")
@XmlAccessorType(XmlAccessType.FIELD)
public class DigitalID {
	
	@IndexID
	private String did;
	
	private String rev;
	
	@JsonProperty("baseid")
	private String baseId;

}
