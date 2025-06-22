package com.vansh.foodOrdering.Service;

import com.vansh.foodOrdering.Repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.vansh.foodOrdering.Model.User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("user not found with username " + username);
        }
        com.vansh.foodOrdering.Model.User user = userOptional.get();
        return new User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
