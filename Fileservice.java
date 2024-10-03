
package blogapp_api.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface Fileservice {
	String UplaodImage(String path,MultipartFile file)throws IOException;
	InputStream getResource(String path,String filename)throws FileNotFoundException;

} 
