package com.westernhills.westernhills.repo;

import com.westernhills.westernhills.entity.admin.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BannerRepository extends JpaRepository<Banner , UUID> {



}
