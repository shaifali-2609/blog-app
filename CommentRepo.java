package blogapp_api.Repositiory;

import org.springframework.data.jpa.repository.JpaRepository;

import blogapp_api.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
