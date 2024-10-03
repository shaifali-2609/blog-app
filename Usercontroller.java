package blogapp_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import blogapp_api.payload.Apiresponse;
import blogapp_api.payload.UserDto;
import blogapp_api.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class Usercontroller {
	@Autowired
	UserService userservice;
	@PostMapping("/")
	public ResponseEntity<UserDto> createuser(@Valid @RequestBody UserDto userd){
		UserDto createuser = this.userservice.createuser(userd);
		return new ResponseEntity<>(createuser,HttpStatus.CREATED);
	}
	@PutMapping("/{userid}")
	public ResponseEntity<UserDto> updateuser(@Valid@RequestBody UserDto userd
			,@PathVariable("userid")int userid){
		UserDto userup = this.userservice.updateuser(userd, userid);
		return ResponseEntity.ok(userup);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userid}")
	public ResponseEntity<Apiresponse> deleteuser(
			@PathVariable("userid")int userid){
		this.userservice.deleteuser(userid);
		return   new ResponseEntity<Apiresponse>(new Apiresponse("user deleted",true),HttpStatus.OK);
		
	}
	@GetMapping("/{userid}")
	public ResponseEntity<UserDto> getuser(@PathVariable("userid")int userid){
		UserDto userdt = this.userservice.getUserById(userid);
		return ResponseEntity.ok(userdt);
		
	}
	@GetMapping("/")
	public  ResponseEntity<List<UserDto>> getall(){
		List<UserDto> allUser = this.userservice.getAllUser();
		return ResponseEntity.ok(allUser)
;		
	}
	
}
