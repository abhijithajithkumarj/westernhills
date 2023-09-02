package com.westernhills.westernhills.service;

import com.westernhills.westernhills.entity.admin.CouponEntity;
import com.westernhills.westernhills.repo.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CouponServiceImpl implements CouponService{


    @Autowired
    private CouponRepository couponRepository;




    @Override
    public List<CouponEntity> getAllCoupons() {
        return null;
    }

    @Override
    public Optional<CouponEntity> getCouponById(Long id) {
        return Optional.empty();
    }

    @Override
    public CouponEntity createCoupon(CouponEntity coupon) {

        CouponEntity coupon1 = new CouponEntity();
        coupon1.setQuantity(coupon.getQuantity());
        coupon1.setCouponCode(coupon.getCouponCode());
        coupon1.setExpirationDate(coupon.getExpirationDate());

        return couponRepository.save(coupon1);


    }

    @Override
    public void deleteCoupon(UUID id) {

    }








}
