package com.westernhills.westernhills.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name="users")
public class  User {
    @Id
    @GeneratedValue
    private Long id;


    private String username;
    private String email;
    private String otp;
    private boolean enabled;
    private boolean deleted;
    private String password;
    private String roles;
    private LocalDateTime otpGeneratedTime;







}

