package com.westernhills.westernhills.dto;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;

}
