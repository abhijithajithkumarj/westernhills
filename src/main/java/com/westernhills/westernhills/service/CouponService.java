package com.westernhills.westernhills.service;


import com.westernhills.westernhills.entity.admin.CouponEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CouponService {


    List<CouponEntity> getAllCoupons();

    Optional<CouponEntity> getCouponById(Long id);

    CouponEntity createCoupon(CouponEntity coupon);

    void deleteCoupon(UUID id);



}
