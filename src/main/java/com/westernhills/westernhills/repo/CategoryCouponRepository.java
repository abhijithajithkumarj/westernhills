package com.westernhills.westernhills.repo;


import com.westernhills.westernhills.entity.admin.CategoryCoupon;
import com.westernhills.westernhills.entity.admin.CategoryUseCoupon;
import com.westernhills.westernhills.entity.admin.ProductCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryCouponRepository extends JpaRepository<CategoryCoupon,UUID> {

    Optional<CategoryCoupon> findOptionalByCouponCode(String couponCode);






}
