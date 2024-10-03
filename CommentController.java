package blogapp_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blogapp_api.payload.Apiresponse;
import blogapp_api.payload.CommentDto;
import blogapp_api.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	@Autowired
	CommentService com;
	@PostMapping("/posts/{postid}/com")
	public ResponseEntity<CommentDto> craetecom(@PathVariable int postid
			,@RequestBody CommentDto comd){
		CommentDto createcommet = this.com.createcommet(comd, postid);
		return new ResponseEntity<CommentDto>(createcommet,HttpStatus.CREATED);
		
	}  
	@DeleteMapping("/com/{comid}")
	public ResponseEntity<Apiresponse> delete(@PathVariable int comid){
		this.com.deletecomment(comid);
		return new ResponseEntity<Apiresponse>(new Apiresponse("comment deleted", true),HttpStatus.OK );
	}

}
