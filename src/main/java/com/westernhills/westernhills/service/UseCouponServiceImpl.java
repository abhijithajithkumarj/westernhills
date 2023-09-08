package com.westernhills.westernhills.service;

import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.entity.admin.ProductCoupon;
import com.westernhills.westernhills.entity.admin.UseCoupon;
import com.westernhills.westernhills.entity.userEntity.User;
import com.westernhills.westernhills.repo.CouponRepository;
import com.westernhills.westernhills.repo.ProductRepository;
import com.westernhills.westernhills.repo.UseCouponRepository;
import com.westernhills.westernhills.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UseCouponServiceImpl implements UseCouponService{

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private UseCouponRepository useCouponRepository;


    @Autowired
    private CouponRepository couponRepository;


    @Autowired
    private UserRepository userRepository;




    @Override
    public List<UseCoupon> getUseCoupons(UseCoupon useCoupon) {
        return null;
    }

    @Override
    public double discount(String discountCoupon) {
        return 0;
    }


    @Override
    public double discountProduct(String discountCoupon, String username) {
        Optional<ProductCoupon> couponOptional = couponRepository.findOptionalByCouponCode(discountCoupon);

        if (couponOptional.isPresent()) {
            ProductCoupon coupon = couponOptional.get();
            Product product = coupon.getProduct();

                int count = coupon.getQuantity();

            if (count > 0) {
                List<UseCoupon> couponUser = useCouponRepository.findByUser_Username(username);
                User user = userRepository.findByUsername(username).orElse(null);
                boolean isAppliedProductCoupon = couponUser.stream()
                        .anyMatch(UseCoupon::isAppliedProductCoupon);

                LocalDate expirationDate = coupon.getExpirationDate();
                LocalDate currentDate = LocalDate.now();

                if (!isAppliedProductCoupon || user == null) {
                    if (expirationDate != null && expirationDate.isAfter(currentDate)) {

                        UseCoupon useCoupon = new UseCoupon();
                        double couponPercentage = coupon.getPercentage();
                        double productPrice = product.getSelPrice();
                        double discount = (couponPercentage / 100.0) * productPrice;
                        double discountFinalPrice = productPrice - discount;

                        useCoupon.setProductCoupon(coupon);
                        useCoupon.setAppliedProductCoupon(true);
                        useCoupon.setActivated(true);
                        useCoupon.setProduct(product);
                        useCoupon.setUser(user);
                        useCoupon.setDiscountPrice(discount);
                        useCouponRepository.save(useCoupon);


                        coupon.setQuantity(count - 1);
                        couponRepository.save(coupon);

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
