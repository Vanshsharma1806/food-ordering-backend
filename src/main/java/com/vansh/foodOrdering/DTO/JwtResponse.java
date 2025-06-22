package com.vansh.foodOrdering.DTO;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String username;
    private String email;
}
