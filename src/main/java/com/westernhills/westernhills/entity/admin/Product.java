package com.westernhills.westernhills.entity.admin;

import com.westernhills.westernhills.entity.SuperEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "product")
public class Product extends SuperEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;

    private String name;

    @Lob
    private String description;
    private double costPrice;
    private double selPrice;
    private boolean enabled=true;
    private int averageRating = 0;

    //relationships
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;



    @OneToMany(mappedBy = "product_id", cascade = CascadeType.ALL )
    private List<Image> images;









}
