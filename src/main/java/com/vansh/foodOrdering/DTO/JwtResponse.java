package com.vansh.foodOrdering.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String id;
    private String token;
    private String username;
    private String email;
}
