package com.westernhills.westernhills.entity.userEntity;


import com.westernhills.westernhills.entity.admin.Product;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;



    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;



    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

}
