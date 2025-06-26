package com.vansh.foodOrdering.DTO;

import lombok.Data;

@Data
public class SignupRequest {
    private String Username;
    private String email;
    private String password;

}
