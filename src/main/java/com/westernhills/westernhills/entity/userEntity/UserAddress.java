package com.westernhills.westernhills.entity.userEntity;


import com.westernhills.westernhills.entity.SuperEntity;
import com.westernhills.westernhills.entity.userEntity.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(name="address")
public class UserAddress extends SuperEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private String area;
    private String town;
    private String city;
    private String state;
    private String pin;
    private String houseName;
    private String landMarker;
    private boolean defaultAddress;
    private boolean enabled = true;


    @ManyToOne
    @JoinColumn(name = "users")
    private User user;











}
