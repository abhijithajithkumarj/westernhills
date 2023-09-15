package com.westernhills.westernhills.service.coupon;


import com.westernhills.westernhills.entity.admin.*;
import com.westernhills.westernhills.entity.userEntity.User;
import com.westernhills.westernhills.repo.CategoryCouponRepository;
import com.westernhills.westernhills.repo.CategoryUseCouponRepository;
import com.westernhills.westernhills.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsedCategoryCouponServiceImpl implements UsedCategoryCouponService {



    @Autowired
    private CategoryCouponRepository categoryCouponRepository;


    @Autowired
    private CategoryUseCouponRepository categoryUseCouponRepository;


    @Autowired
    private UserRepository userRepository;


    @Override
    public List<CategoryUseCoupon> getUseCoupons(CategoryUseCoupon useCoupon) {
        return null;
    }

    @Override
    public double discount(String discountCoupon) {
        return 0;
    }

    @Override
    public double discountProduct(String discountCoupon, String username) {


        Optional<CategoryCoupon> couponOptional=categoryCouponRepository.findOptionalByCouponCode(username);



        if (couponOptional.isPresent()) {
            CategoryCoupon coupon = couponOptional.get();
            Category Category = coupon.getCategory();
            int count = coupon.getQuantity();


            if (count > 0) {

                List<CategoryUseCoupon> categoryUseCoupons= categoryUseCouponRepository.findByUser_Username(username);
                User user = userRepository.findByUsername(username).orElse(null);
                boolean isAppliedProductCoupon = categoryUseCoupons.stream()
                        .anyMatch(CategoryUseCoupon::isAppliedProductCoupon);



                LocalDate expirationDate = coupon.getExpirationDate();
                LocalDate currentDate = LocalDate.now();



                if (!isAppliedProductCoupon || user == null) {
                    if (expirationDate != null && expirationDate.isAfter(currentDate)) {



                        CategoryUseCoupon useCoupon = new CategoryUseCoupon();
                        double couponPercentage = coupon.getPercentage();
                        double categoryPrice = 150;
                        double discount = (couponPercentage / 100.0) * categoryPrice;
                        double discountFinalPrice = categoryPrice - discount;

                        useCoupon.setCategoryCoupon(coupon);
                        useCoupon.setAppliedProductCoupon(true);
                        useCoupon.setActivated(true);
                        useCoupon.setCategory(Category);
                        useCoupon.setUser(user);
                        useCoupon.setDiscountPrice(discount);
                        useCoupon.setCouponStatus(CouponStatus.Used);
                        categoryUseCouponRepository.save(useCoupon);
                        coupon.setQuantity(count - 1);
                        categoryCouponRepository.save(coupon);

                        return discount;
                    }
                }


            }



        } else {
            System.out.println("Coupon not found or no available coupons.");
        }


        return 0.0;
    }
}
