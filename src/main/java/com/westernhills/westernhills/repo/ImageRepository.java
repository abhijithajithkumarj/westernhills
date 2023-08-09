package com.westernhills.westernhills.repo;

import com.westernhills.westernhills.entity.admin.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    Image save(Image image);


}
