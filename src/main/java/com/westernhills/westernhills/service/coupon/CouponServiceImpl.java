package com.westernhills.westernhills.service.coupon;

import com.westernhills.westernhills.entity.admin.ProductCoupon;
import com.westernhills.westernhills.repo.CouponRepository;
import com.westernhills.westernhills.service.coupon.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CouponServiceImpl implements CouponService {


    @Autowired
    private CouponRepository couponRepository;




    @Override
    public List<ProductCoupon> getAllCoupons() {
        return null;
    }

    @Override
    public Optional<ProductCoupon> getCouponById(Long id) {
        return Optional.empty();
    }

    @Override
    public ProductCoupon createCoupon(ProductCoupon coupon) {

        ProductCoupon coupon1 = new ProductCoupon();
        coupon1.setQuantity(coupon.getQuantity());
        coupon1.setCouponCode(coupon.getCouponCode());
        coupon1.setExpirationDate(coupon.getExpirationDate());
        coupon1.setProduct(coupon.getProduct());
        coupon1.setPercentage(coupon.getPercentage());
        return couponRepository.save(coupon1);


    }

    @Override
    public void deleteCoupon(UUID id) {
        couponRepository.deleteById(id);

    }








}
