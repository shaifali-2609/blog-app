package blogapp_api.iml;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blogapp_api.Repositiory.CommentRepo;
import blogapp_api.Repositiory.PostRepo;
import blogapp_api.entity.Comment;
import blogapp_api.entity.Post;
import blogapp_api.exception.ResourceNotFoundException;
import blogapp_api.payload.CommentDto;
import blogapp_api.service.CommentService;
@Service
public class Comimpl implements CommentService  {
@Autowired
CommentRepo comrepo;
@Autowired
PostRepo postrepo;

@Autowired
ModelMapper model;
	@Override
	public CommentDto createcommet(CommentDto com,int postid ) {
		Post post = this.postrepo.findById(postid)
		.orElseThrow(()-> new ResourceNotFoundException("post", "postid", postid));
		Comment com1=this.model.map(com, Comment.class );
		com1.setPost(post);
		this.comrepo.save(com1);
		return this.model.map(com1, CommentDto.class);
	}

	@Override
	public void deletecomment(int comid) {
		Comment com=this.comrepo.findById(comid)
				.orElseThrow(()-> new ResourceNotFoundException("comment", "comid", comid));
		this.comrepo.delete(com);
		
	}

}
