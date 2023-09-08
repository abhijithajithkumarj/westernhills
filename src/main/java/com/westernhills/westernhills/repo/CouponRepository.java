package com.westernhills.westernhills.repo;

import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.entity.admin.ProductCoupon;
import com.westernhills.westernhills.entity.admin.UseCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<ProductCoupon, UUID> {


    ProductCoupon findByCouponCode(String couponCode);
    Optional<ProductCoupon> findOptionalByCouponCode(String couponCode);









}
