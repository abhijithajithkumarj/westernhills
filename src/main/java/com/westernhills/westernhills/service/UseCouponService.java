package com.westernhills.westernhills.service;

import com.westernhills.westernhills.entity.admin.UseCoupon;

import java.util.List;

public interface UseCouponService {

    List<UseCoupon> getUseCoupons(UseCoupon useCoupon);

    double discount(String discountCoupon);


    double discountProduct(String discountCoupon,String username);



}
