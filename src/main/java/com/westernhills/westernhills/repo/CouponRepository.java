package com.westernhills.westernhills.repo;

import com.westernhills.westernhills.entity.admin.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CouponRepository extends JpaRepository<CouponEntity, UUID> {





}
