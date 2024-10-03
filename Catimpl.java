package blogapp_api.iml;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blogapp_api.Repositiory.CategoryRepo;
import blogapp_api.entity.Category;
import blogapp_api.exception.ResourceNotFoundException;
import blogapp_api.payload.CategoryDto;
import blogapp_api.service.CategoryService;
@Service
public class Catimpl implements CategoryService {
@Autowired
ModelMapper model;
@Autowired
CategoryRepo catRepo;
	@Override
	public CategoryDto createCategory(CategoryDto catdto) {
	Category cat=this.model.map(catdto, Category.class);
	Category catg = this.catRepo.save(cat);
		return this.model.map(catg, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto catdto, int categoryId) {
		Category cat=this.catRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("category","categoryId",categoryId));
		cat.setCategoryTitle(catdto.getCategoryTitle());
		cat.setCategoryDescription(catdto.getCategoryDescription());
		Category catt = this.catRepo.save(cat);
		return this.model.map(catt, CategoryDto.class);
	}

	@Override
	public CategoryDto delete(int categoryid) {
		Category cat=this.catRepo.findById(categoryid)
				.orElseThrow(()->new ResourceNotFoundException("category","categoryId",categoryid));
				this.catRepo.delete(cat);
		
		return null;
	}

	@Override
	public CategoryDto get(int categoryid) {
		Category Cat=this.catRepo.findById(categoryid)				
				.orElseThrow(()->new ResourceNotFoundException("category","categoryId",categoryid));

		return  this.model.map(Cat, CategoryDto.class);
	}

	@Override
	
	 public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.catRepo.findAll();
        List<CategoryDto> collect = categories.stream().map(category -> this.model.map(category, CategoryDto.class)).collect(Collectors.toList());
        
        return collect;
        
      }
	/*@Override
	public List<CategoryDto> getallcategory() {
		List<Category> cat=this.catRepo.findAll();
		List<CategoryDto> catdt=cat.stream()
				.map(ca->this.model.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return catdt;
	}*/

}
