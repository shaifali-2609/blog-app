package blogapp_api.service;

import java.util.List;

import blogapp_api.entity.Category;
import blogapp_api.payload.CategoryDto;

public interface CategoryService {
	 CategoryDto createCategory(CategoryDto catdto);
	  CategoryDto updateCategory(CategoryDto  catdto,int categoryId);
	
	  CategoryDto delete(int categoryid);
  CategoryDto get(int categoryid);
	List<CategoryDto> getAllCategories() ;
}