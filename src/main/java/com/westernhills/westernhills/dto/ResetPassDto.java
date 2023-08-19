package com.westernhills.westernhills.dto;


import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResetPassDto {

    private String email;
    private String otp;
    private String password;


}
