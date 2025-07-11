package com.vansh.foodOrdering.Service;

import com.vansh.foodOrdering.DTO.JwtResponse;
import com.vansh.foodOrdering.DTO.LoginRequest;
import com.vansh.foodOrdering.DTO.SignupRequest;
import com.vansh.foodOrdering.Model.User;
import com.vansh.foodOrdering.Repository.UserRepository;
import com.vansh.foodOrdering.Utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public UserService(UserDetailsServiceImpl userDetailsService, UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String signup(SignupRequest signupRequest){
        String email = signupRequest.getEmail();
        String username = signupRequest.getUsername();
        if(userRepository.existsByEmail(email) ){
            throw new RuntimeException("Email already registered.");
        }
        if(userRepository.existsByUsername(username)){
            throw new RuntimeException(("username not available."));
        }
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRoles(roles);
        userRepository.save(user);
        return "User registered successfully.";
    }

    public JwtResponse login(LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail(), user.getRoles());
        return new JwtResponse(user.getId(), token, user.getUsername(),user.getEmail());
    }
}
