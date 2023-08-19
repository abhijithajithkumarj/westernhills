package com.westernhills.westernhills.entity.userEntity;


import com.westernhills.westernhills.entity.SuperEntity;
import com.westernhills.westernhills.entity.admin.Product;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Cart extends SuperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;



    private int quantity;


    @ManyToOne
    @JoinColumn(name = "user")
    private User user;


    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;





}
