package com.westernhills.westernhills.service.coupon;

import com.westernhills.westernhills.entity.admin.CategoryCoupon;
import com.westernhills.westernhills.repo.CategoryCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryCouponServiceImpl  implements CategoryCouponService {


    @Autowired
    private CategoryCouponRepository categoryCouponRepository;



    @Override
    public void createCoupon(CategoryCoupon coupon) {
        CategoryCoupon coupon1 = new CategoryCoupon();
        coupon1.setQuantity(coupon.getQuantity());
        coupon1.setCouponCode(coupon.getCouponCode());
        coupon1.setExpirationDate(coupon.getExpirationDate());
        coupon1.setCategory(coupon.getCategory());
        coupon1.setPercentage(coupon.getPercentage());
        categoryCouponRepository.save(coupon1);
    }


}
