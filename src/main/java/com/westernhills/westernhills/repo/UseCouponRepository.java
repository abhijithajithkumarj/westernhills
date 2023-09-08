package com.westernhills.westernhills.repo;

import com.westernhills.westernhills.entity.admin.UseCoupon;
import com.westernhills.westernhills.entity.userEntity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UseCouponRepository extends JpaRepository<UseCoupon, UUID> {


    List<UseCoupon> findByUser_Username(String username);










}
