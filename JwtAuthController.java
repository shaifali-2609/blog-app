package blogapp_api.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blogapp_api.entity.User;
import blogapp_api.payload.JwtAuthResponse;
import blogapp_api.payload.JwtAuthrequest;
import blogapp_api.payload.UserDto;
import blogapp_api.security.JwtHelper;
import blogapp_api.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class JwtAuthController {
	
	@Autowired
	UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    ModelMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createAuthenticationToken(@RequestBody JwtAuthrequest request) {
        authenticate(request.getUsername(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtHelper.generateToken(userDetails);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
      response.setUser(this.mapper.map((User)userDetails, UserDto.class));

        return ResponseEntity.ok(response);
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new DisabledException("username and password is invaild");
        }
    }
    @PostMapping("/register")
   
    public ResponseEntity<UserDto> createUser( @ Valid @RequestBody UserDto userDto) {
        // Call service to save user
        UserDto createdUser = userService.createuser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
   
  
}   

 

