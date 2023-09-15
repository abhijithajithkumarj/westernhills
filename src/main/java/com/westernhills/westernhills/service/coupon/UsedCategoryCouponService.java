package com.westernhills.westernhills.service.coupon;

import com.westernhills.westernhills.entity.admin.CategoryUseCoupon;
import com.westernhills.westernhills.entity.admin.UseCoupon;

import java.util.List;

public interface UsedCategoryCouponService {

    List<CategoryUseCoupon> getUseCoupons(CategoryUseCoupon useCoupon);

    double discount(String discountCoupon);


    double discountProduct(String discountCoupon,String username);
}
