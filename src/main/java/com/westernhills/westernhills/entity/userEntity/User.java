package com.westernhills.westernhills.entity.userEntity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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



    @OneToMany (mappedBy = "user",cascade = CascadeType.ALL )
    @ToString.Exclude
    private List<UserAddress> addresses=new ArrayList<UserAddress>();






    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Cart> carts=new ArrayList<Cart>();




}

