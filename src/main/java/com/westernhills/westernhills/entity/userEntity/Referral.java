package com.westernhills.westernhills.entity.userEntity;


import com.westernhills.westernhills.entity.SuperEntity;
import com.westernhills.westernhills.entity.admin.CouponStatus;
import com.westernhills.westernhills.entity.wallet.Wallet;
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
@Builder
public class Referral extends SuperEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private String referralCode;


    @Enumerated(EnumType.STRING)
    private CouponStatus couponStatus;



    @ManyToOne
    @JoinColumn(name = "user")
    private User user;










}
