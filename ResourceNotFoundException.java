package blogapp_api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException  extends RuntimeException{
	String resoucename;
	String fieldname;
	
	long fieldvalue;

	public ResourceNotFoundException(String resoucename, String fieldname, long fieldvalue) {
		super(String.format("%s not found with %s:%s",resoucename,fieldname,fieldvalue));
		this.resoucename = resoucename;
		this.fieldname = fieldname;
		this.fieldvalue = fieldvalue;
	}


}
