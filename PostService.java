package blogapp_api.service;

import java.util.List;

import blogapp_api.payload.PostDto;
import blogapp_api.payload.PostResponse;

public interface PostService  {
PostDto createPost(PostDto postdto ,int userid,int categoryid);
PostDto UpdatePost(PostDto postdto,int postid);
PostDto deletePost(int postid);
PostDto getPost(int postid);
PostResponse getAllPost(int pageno,int pagesize ,String sortby,String sortDir);
List<PostDto> getPostByUser(int userid);
List<PostDto> getPostByCategory(int categoryid);
List<PostDto> searchPost(String keywords);
}
