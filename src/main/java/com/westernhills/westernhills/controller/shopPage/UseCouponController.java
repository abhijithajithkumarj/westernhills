package com.westernhills.westernhills.controller.shopPage;


import com.westernhills.westernhills.dto.CouponDTO;
import com.westernhills.westernhills.repo.CheckOutRepository;
import com.westernhills.westernhills.service.CartService;
import com.westernhills.westernhills.service.coupon.UseCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class UseCouponController {


    @Autowired
    private CartService cartService;



    @Autowired
    private CheckOutRepository checkOutRepository;


    @Autowired
    private UseCouponService useCouponService;




    @GetMapping("/couponAdd")
    public String addCouponAdd(@ModelAttribute CouponDTO couponDTO,
                               @AuthenticationPrincipal(expression = "username") String username) {

        String coupon = couponDTO.getCoupon();

        System.out.println("working proccess");
        useCouponService.discountProduct(coupon, username);
        return "redirect:/showCheckout";
    }



    @GetMapping("/categoryCouponAdd")
    public String addCategoryCouponAdd(@ModelAttribute CouponDTO couponDTO,
                                       @AuthenticationPrincipal(expression = "username") String username){
        String coupon = couponDTO.getCoupon();
        useCouponService.discountProduct(coupon, username);
        return "redirect:/customers";

    }








}
