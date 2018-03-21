package cdis.indexd.constraints;

public enum IndexHashType {
	
	MD5, SHA1, SHA256, SHA512, CRC, ETAG;
	
	public boolean isValid(String val) {
		if(this == MD5) {
			return val.matches("[a-fA-F0-9]{32}");
		}else if(this == SHA1) {
			return val.matches("[a-fA-F0-9]{40}");
		}else if(this == SHA256) {
			return val.matches("[a-fA-F0-9]{64}");
		}else if(this == SHA512) {
			return val.matches("[a-fA-F0-9]{128}");
		}else if(this == CRC) {
			return val.matches("[a-fA-F0-9]{8}");
		}else if(this == ETAG) {
			return val.matches("[a-fA-F0-9]{32}(-\\d+)?");
		}
		
		return false;
	}
	
	public static IndexHashType fromString(String val) {
		IndexHashType hashType = null;
		try {
			hashType = valueOf(val);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hashType;
	}

}
