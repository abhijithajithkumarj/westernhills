package com.westernhills.westernhills.entity.admin;


import com.westernhills.westernhills.entity.SuperEntity;
import com.westernhills.westernhills.entity.userEntity.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@Table
@Builder
public class CategoryUseCoupon extends SuperEntity {




    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;


    private boolean appliedProductCoupon;


    private double discountPrice;



    @Enumerated(EnumType.STRING)
    private CouponStatus couponStatus;




    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;



    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "categoryCoupon_id")
    private CategoryCoupon categoryCoupon;




}
