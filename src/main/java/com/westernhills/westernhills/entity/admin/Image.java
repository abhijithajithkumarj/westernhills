package com.westernhills.westernhills.entity.admin;


import com.westernhills.westernhills.entity.SuperEntity;
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
public class Image extends SuperEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;

    private String fileName;

    //relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product_id;






    public Image(String fileName, Product product) {
        this.fileName = fileName;
        this.product_id = product;
    }



}
