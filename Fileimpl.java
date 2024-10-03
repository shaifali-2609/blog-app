package blogapp_api.iml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import blogapp_api.service.Fileservice;

@Service
public class Fileimpl implements Fileservice {

    @Override
    public String UplaodImage(String path, MultipartFile file) throws IOException {
        // Get the original file name
        String name = file.getOriginalFilename();
        

        // Generate a random ID and concatenate it with the file name
        String randomId = UUID.randomUUID().toString();
        String concat = randomId + name.substring(name.lastIndexOf("."));
        
        // Construct the full file path
       String filePath = path+File.separator+concat;
        
        // Create the directory if it does not exist
        File f = new File(path);
        if (!f.exists()) {
        	f.mkdir();
            
              
            }
        Files.copy(file.getInputStream(),Paths.get(filePath) );
        return concat;
        }
        
        

    @Override
    public InputStream getResource(String path, String filename) throws FileNotFoundException {
      String fpath=path+File.separator+filename;
      InputStream is= new FileInputStream(fpath);
        return is;
    }
}
