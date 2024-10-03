package blogapp_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blogapp_api.Repositiory.CategoryRepo;
import blogapp_api.payload.Apiresponse;
import blogapp_api.payload.CategoryDto;
import blogapp_api.service.CategoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cat")
public class CatregoryController {
	@Autowired
	CategoryService catser;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createcategory(@Valid@RequestBody CategoryDto catdt){
		CategoryDto ct=this.catser.createCategory(catdt);
		return new  ResponseEntity<>(ct,HttpStatus.CREATED);
		
	}
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getallcategory(){
		List<CategoryDto> all=this.catser.getAllCategories();
		return   ResponseEntity.ok(all);
	}
	@GetMapping("/{categoryid}")
	public ResponseEntity<CategoryDto> getsinglecatogory(@PathVariable("categoryid") int catid){
		CategoryDto categoryDto = this.catser.get(catid);
		return ResponseEntity.ok(categoryDto);
	}
	@PutMapping("/{categoryid}")
	public ResponseEntity<CategoryDto> update (@PathVariable("categoryid") int catid
			,@RequestBody CategoryDto catd){
		CategoryDto updateCategory = this.catser.updateCategory(catd, catid);
		return ResponseEntity.ok(updateCategory);
	}
	@DeleteMapping("/{categoryid}")
	public ResponseEntity<Apiresponse> deletecat(@PathVariable("categoryid")int catid){
		 this.catser.delete(catid);
		return new ResponseEntity<Apiresponse>(new Apiresponse("Category deletd",true),HttpStatus.OK);
	}
}
