package com.westernhills.westernhills.controller.admincontroller;


import com.westernhills.westernhills.entity.admin.CouponEntity;
import com.westernhills.westernhills.repo.CouponRepository;
import com.westernhills.westernhills.service.CouponService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CouponController {


    @Autowired
    private CouponRepository couponRepository;


    @Autowired
    private CouponService couponService;

    @GetMapping("/ShowAddCoupons")
    public String showAddCoupon(Model model) {
        model.addAttribute("coupon", new CouponEntity());
        return "admin/Coupon-add";
    }



    @GetMapping("/addCoupon")
    public String addCoupon(@ModelAttribute CouponEntity coupon){
        couponService.createCoupon(coupon);
        return "redirect:/ShowAddCoupons";
    }





    @GetMapping("/showCoupon")
    public String showCoupon(Model model){
        List<CouponEntity> coupons = couponRepository.findAll()
                        .stream()
                                .filter(coupon -> !coupon.isDeleted())
                                        .toList();
        model.addAttribute("coupons", coupons);
        System.out.println(coupons);
          return "admin/showCoupons";

    }
















}
