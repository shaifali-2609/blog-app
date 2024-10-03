package blogapp_api.service;

import blogapp_api.payload.CommentDto;

public interface CommentService {
	CommentDto createcommet(CommentDto com, int postid);
	void deletecomment(int comid);

}
