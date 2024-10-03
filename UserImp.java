package blogapp_api.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import blogapp_api.Repositiory.RoleRepo;
import blogapp_api.Repositiory.UserRepository;
import blogapp_api.config.AppConstant;
import blogapp_api.entity.Role;
import blogapp_api.entity.User;
import blogapp_api.exception.ResourceNotFoundException;
import blogapp_api.payload.UserDto;
import blogapp_api.service.UserService;

@Service // Ensure this annotation is present
public class UserImp implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto createuser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepository.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateuser(UserDto userDto, int userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userid", userId));
        
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(user.getAbout());
        user.setPassword(user.getPassword());
        User updatedUser = this.userRepository.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(int userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userid", userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = this.userRepository.findAll();
        return users.stream().map(this::userToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteuser(int userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userid", userId));
        this.userRepository.delete(user);
    }

    private User dtoToUser(UserDto userDto) {
        return this.modelMapper.map(userDto, User.class);
    }

    private UserDto userToDto(User user) {
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto resister(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Role role = this.roleRepo.findById(AppConstant.normal_user).get();
        user.getRoles().add(role);
        User savedUser = this.userRepository.save(user);
        return this.modelMapper.map(savedUser, UserDto.class);
    }
}
