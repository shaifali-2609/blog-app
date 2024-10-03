package blogapp_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import blogapp_api.Repositiory.UserRepository;
import blogapp_api.entity.User;
import blogapp_api.exception.ResourceNotFoundException;

@Service
public class CustomUserdetailService implements UserDetailsService {

    @Autowired
    private UserRepository userrepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = this.userrepo.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", 0));
      
        return user;
    }
}
