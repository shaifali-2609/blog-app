package blogapp_api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import blogapp_api.config.AppConstant;
import blogapp_api.payload.Apiresponse;
import blogapp_api.payload.PostDto;
import blogapp_api.payload.PostResponse;
import blogapp_api.service.Fileservice;
import blogapp_api.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class PostControllr {
	@Autowired
	Fileservice fileser;
	@Autowired
	PostService postser;
	@Value("${project.image}")
	private String path;
	@PostMapping("/users/{userid}/cat/{categoryid}/image/posts")
	public ResponseEntity<PostDto> createpst( @Valid@RequestBody PostDto postdto
			,@PathVariable int userid,
			@PathVariable int categoryid){
			

		PostDto post = this.postser.createPost(postdto, userid, categoryid);
		
		return new  ResponseEntity<PostDto>(post,HttpStatus.CREATED);
	

}
	 @GetMapping("/users/{userId}/posts")
	    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable int userId) {
	        List<PostDto> posts = this.postser.getPostByUser(userId);
	        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	    }
	@GetMapping("cat/{categoryid}/posts")
	public ResponseEntity<List<PostDto>> getbycat(@PathVariable int categoryid){
		List<PostDto> postByCategory = this.postser.getPostByCategory(categoryid);
		return new ResponseEntity<List<PostDto>>(postByCategory,HttpStatus.OK);
	}
	@GetMapping("/posts/{postid}")
	public ResponseEntity<PostDto> getpost( @PathVariable int postid){
		PostDto post = this.postser.getPost(postid);
		return ResponseEntity.ok(post);
	}
	@GetMapping("posts")
	public ResponseEntity<PostResponse> getallpost(@RequestParam(value="pageno",
	defaultValue = AppConstant.page_number,required = false) int p,@RequestParam(value="pagesize",defaultValue = AppConstant.page_size,required = false)
		int s ,@RequestParam(value="sortby",defaultValue = AppConstant.sort_by ,required = false)	String x
		,@RequestParam(value="Sortdir",defaultValue = AppConstant.sort_dir,required = false) String sortDir)
	{
		 PostResponse allPost = this.postser.getAllPost(p, s,x,sortDir);
		return ResponseEntity.ok(allPost);
	}
	
	@PutMapping("/posts/{postid}")
	public ResponseEntity<PostDto> update(@RequestBody PostDto dto ,@PathVariable int postid){
		PostDto updatePost = this.postser.UpdatePost(dto, postid);
		return ResponseEntity.ok(updatePost);
	}
	@DeleteMapping("/posts/{postid}")
	public ResponseEntity<Apiresponse> delte(@PathVariable int postid){
		this.postser.deletePost(postid);
		return new ResponseEntity<Apiresponse>(new Apiresponse("post deleted succsefully",true), HttpStatus.OK);
	}
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>>search(@PathVariable("keywords") String keyword){
		
		List<PostDto> searchPost = this.postser.searchPost(keyword);
		return new ResponseEntity<List<PostDto>>(searchPost,HttpStatus.OK);
	}
	
	@PostMapping("posts/imagename/upload/{postid}")
	public ResponseEntity<PostDto> uploadimage(@RequestParam("imagename") MultipartFile image, @PathVariable int postid) throws IOException {
	 
	    String uploadImageName = this.fileser.UplaodImage(path, image);
	    System.out.println("Uploaded Image Name: " + uploadImageName); // Log the new image name

	    
	    PostDto post = this.postser.getPost(postid);
	  
	    System.out.println("Before Update - Post Image Name: " + post.getImagename()); // Log current image name before update
	    post.setImagename(uploadImageName);
      PostDto updatedPost = this.postser.UpdatePost(post, postid);
	    System.out.println("After Update - Post Image Name: " + updatedPost.getImagename()); // Log updated image name
     return ResponseEntity.ok(updatedPost);
	}
	@GetMapping( value="/image/{imagename}" ,produces=MediaType.IMAGE_PNG_VALUE)
	public void Getimage(@PathVariable("imagename") 
	String image,HttpServletResponse response) throws IOException{
		InputStream resource = this.fileser.getResource(path, image);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
	}


}