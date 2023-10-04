package com.westernhills.westernhills.dto;


import com.westernhills.westernhills.entity.admin.Category;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {


    private UUID id;
    private String name;
    private String description;
    private double costPrice;
    private double selPrice;
    private Long   categoryId;

    private Long   stock;
    private Category category;
    private boolean deleted;
    private boolean activated;
    private List<MultipartFile> productImages = new ArrayList<>();



}
