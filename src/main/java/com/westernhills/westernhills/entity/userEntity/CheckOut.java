package com.westernhills.westernhills.entity.userEntity;

import com.westernhills.westernhills.entity.SuperEntity;
import com.westernhills.westernhills.entity.admin.Product;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Builder
public class CheckOut extends SuperEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;


    @OneToOne
    @JoinColumn(name="address")
    private UserAddress userAddress;


    @OneToOne
    @JoinColumn(name = "user")
    private User user;



    private int count ;


    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;



    @OneToOne
    @JoinColumn(name="product")
    private Product product;



    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;





}
