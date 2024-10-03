package blogapp_api.Repositiory;

import org.springframework.data.jpa.repository.JpaRepository;

import blogapp_api.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
