package com.westernhills.westernhills.entity.admin;


import com.westernhills.westernhills.entity.SuperEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
public class Banner extends SuperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;


    private String name;

    private int placement;

    private boolean defaultBanner;



    @Lob
    private String description;


    @OneToMany(mappedBy = "banner_id", cascade = CascadeType.ALL )
    private List<BannerImage> bannerImages;









}
