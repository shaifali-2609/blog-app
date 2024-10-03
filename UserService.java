package blogapp_api.service;

import java.util.List;

import blogapp_api.payload.UserDto;

public interface UserService  {
	 UserDto resister(UserDto user);
UserDto createuser(UserDto user);
UserDto  updateuser(UserDto user, int userid);
UserDto getUserById(int userid);
 List<UserDto> getAllUser();
void deleteuser(int userid);

}
