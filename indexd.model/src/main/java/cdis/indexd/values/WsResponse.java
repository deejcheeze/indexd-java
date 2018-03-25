package cdis.indexd.values;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.NonNull;

/**
 * A generic response object used a lot for API response 
 * @author Rowland Ogwara
 *
 * @param <T> return payload Type
 */
@Data 
@XmlRootElement(name="response")
@XmlAccessorType(XmlAccessType.FIELD)
public class WsResponse<T> {
	
	/**
	 * code = 0 means success
	 * code != 0 means some form of error. The error must be further described using
	 * the message and/or status fields
	 */
	private int code;
	
	/**
	 * text representation of the code
	 */
	@NonNull
	private String status;
	
	/**
	 * Detailed message associated with the error
	 */
	@NonNull
	private String message;
	
	/**
	 * Response payload, Null for all errors
	 */
	private T entity;
	
	public WsResponse() {
		this(0, "Successful");
	}
	
	public WsResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public boolean isOk() {
		return this.code == 0;
	}
	
	public String getStatus() {
		return isOk()? "OK": "Error";
	}
	
}
