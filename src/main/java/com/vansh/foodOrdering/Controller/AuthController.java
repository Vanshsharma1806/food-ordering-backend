package com.vansh.foodOrdering.Controller;

import com.vansh.foodOrdering.DTO.JwtResponse;
import com.vansh.foodOrdering.DTO.LoginRequest;
import com.vansh.foodOrdering.DTO.SignupRequest;
import com.vansh.foodOrdering.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest){
        String msg = userService.signup(signupRequest);
        return ResponseEntity.ok(msg);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        JwtResponse jwtResponse = userService.login(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

}
