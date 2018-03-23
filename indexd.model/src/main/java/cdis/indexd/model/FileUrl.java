package cdis.indexd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.URL;

import lombok.Data;

@Data
public class FileUrl implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6741205779091223573L;
	
	private List<@URL String> urls;
	
	public void addUrl(String url) {
		if(urls == null) {
			urls = new ArrayList<>();
		}
		urls.add(url);
	}

}
