package com.westernhills.westernhills.entity.wallet;

import com.westernhills.westernhills.entity.SuperEntity;
import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.entity.userEntity.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
public class Wallet extends SuperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;



    private Double returnPayment;



    @ManyToOne
    @JoinColumn(name = "user")
    private User user;







}
