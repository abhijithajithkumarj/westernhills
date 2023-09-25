package com.westernhills.westernhills.service.coupon;


import com.westernhills.westernhills.entity.admin.ProductCoupon;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CouponService {


    List<ProductCoupon> getAllCoupons();

    Optional<ProductCoupon> getCouponById(Long id);

    ProductCoupon createCoupon(ProductCoupon coupon);

    void deleteCoupon(UUID id);



}
