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
@Builder
public class BannerImage extends SuperEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;




    private String fileName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banner_id")
    private  Banner banner_id;




    public BannerImage(String fileName,  Banner banner) {
        this.fileName = fileName;
        this.banner_id = banner;
    }


}
