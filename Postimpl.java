package blogapp_api.iml;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import blogapp_api.Repositiory.CategoryRepo;
import blogapp_api.Repositiory.PostRepo;
import blogapp_api.Repositiory.UserRepository;
import blogapp_api.entity.Category;
import blogapp_api.entity.Post;
import blogapp_api.entity.User;
import blogapp_api.exception.ResourceNotFoundException;
import blogapp_api.payload.PostDto;
import blogapp_api.payload.PostResponse;
import blogapp_api.service.PostService;
@Service
public class Postimpl implements PostService  {
@Autowired
CategoryRepo catrepo;
@Autowired
UserRepository userrepo;
@Autowired
PostRepo postrepo;
@Autowired
ModelMapper model;

 public PostDto createPost(PostDto postdto, int userid, int categoryid) {
    User user = this.userrepo.findById(userid)
            .orElseThrow(() -> new ResourceNotFoundException("user", "userid", userid));
    
    
    Category cat = this.catrepo.findById(categoryid)
            .orElseThrow(() -> new ResourceNotFoundException("category", "categoryid", categoryid));
    
    Post post = this.model.map(postdto, Post.class);
    
    post.setImagename("default.png");
    
    post.setAdddate(new Date());
    
    post.setCategory(cat);
    post.setUser(user);
    
    Post savedPost = this.postrepo.save(post);
    return this.model.map(savedPost, PostDto.class);}

	@Override
	public PostDto UpdatePost(PostDto postdto, int postid) {
		Post post=this.postrepo.findById(postid)
				.orElseThrow(()-> new ResourceNotFoundException("post", "postid", postid));
		post.setTitle(postdto.getTitle());
post.setContent(postdto.getContent());
post.setImagename(postdto.getImagename());

Post save = this.postrepo.save(post);
return this.model.map(save, PostDto.class);
	}

	@Override
	public PostDto deletePost(int postid) {
		Post post = this.postrepo.findById(postid).orElseThrow(()->new ResourceNotFoundException("post", "postid", postid));
		 this.postrepo.delete(post);
		 return null;
	}

	@Override
	public PostDto getPost(int postid) {
		Post post = this.postrepo.findById(postid)
				.orElseThrow(()->new ResourceNotFoundException("post", "postid", postid));
		return this.model.map(post,PostDto.class);
		
	}

	 @Override
	    public List<PostDto> getPostByUser(int userId) {
	        User user = this.userrepo.findById(userId)
	                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
	        List<Post> posts = this.postrepo.findByUser(user);
	        return posts.stream().map(post -> this.model.map(post, PostDto.class))
	                .collect(Collectors.toList());
	    }

	@Override
	public List<PostDto> getPostByCategory(int categoryid) {
		Category cat=this.catrepo.findById(categoryid)
				.orElseThrow(()->new ResourceNotFoundException("category", "categoryid", categoryid));
		List<Post> byCategory = this.postrepo.findByCategory(cat);
		List<PostDto> collects = byCategory.stream().map(cats->this.model.map(cats, PostDto.class))
		.collect(Collectors.toList());
		return collects;
	}

	@Override
	public List<PostDto> searchPost(String keywords) {
	    List<Post> byTitleContaining = this.postrepo.searchByTitle(keywords);
	    List<PostDto> list = byTitleContaining.stream()
	        .map(posts -> this.model.map(posts, PostDto.class))
	        .collect(Collectors.toList());
	    return list;
	}

@Override
	public PostResponse getAllPost(int pageno,int pagesize ,String sortby ,String sortDir) {
	Sort sort=null;
			if(sortDir.equalsIgnoreCase("Asc")){
				sort=sort.by(sortby).ascending();
			}
			else
				
				sort=sort.by(sortby).descending();
		Pageable p=PageRequest.of(pageno, pagesize, sort);
		 Page<Post> all = this.postrepo.findAll(p);
		  List<PostDto> collect = all.stream().map(posts->this.model.map(posts, PostDto.class)).collect(Collectors.toList());
		PostResponse postr=new PostResponse();
		postr.setContent(collect);
		postr.setPagenumber(all.getNumber());
		postr.setPagesize(all.getSize());
		postr.setTotalelement(all.getTotalElements());
		postr.setTotalpage(all.getTotalPages());
		postr.setLstpage(all.isLast());
		  
		  return postr ;
	}
	

}