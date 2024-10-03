package blogapp_api.Repositiory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import blogapp_api.entity.Category;
import blogapp_api.entity.Post;
import blogapp_api.entity.User;

 public interface PostRepo extends JpaRepository<Post, Integer> {
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	// @Query("select p from post p where p.title LIKE %:keyword%")
	  //  List<Post> searchByTitle(@Param("keyword") String keyword);
	 //@Query("select p from post p where p.title LIKE %:keyword%")

	@Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword%")
    List<Post> searchByTitle(@Param("keyword") String keyword);
	


}