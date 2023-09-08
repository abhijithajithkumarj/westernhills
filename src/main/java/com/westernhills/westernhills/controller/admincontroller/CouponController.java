package com.westernhills.westernhills.controller.admincontroller;


import com.westernhills.westernhills.dto.CouponDTO;
import com.westernhills.westernhills.entity.admin.ProductCoupon;
import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.entity.admin.UseCoupon;
import com.westernhills.westernhills.repo.CouponRepository;
import com.westernhills.westernhills.repo.ProductRepository;
import com.westernhills.westernhills.repo.UseCouponRepository;
import com.westernhills.westernhills.service.CouponService;
import com.westernhills.westernhills.service.UseCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CouponController {


    @Autowired
    private CouponRepository couponRepository;




    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private UseCouponService useCouponService;




    @Autowired
    private UseCouponRepository useCouponRepository;


    @Autowired
    private CouponService couponService;

    @GetMapping("/ShowAddCoupons")
    public String showAddCoupon(Model model) {
        model.addAttribute("coupon", new ProductCoupon());
        List<Product> products=productRepository.findAll()
                .stream()
                .filter(product -> !product.isDeleted())
                .toList();
        model.addAttribute("products", products);
        System.out.println(products);
        return "admin/Coupon-add";
    }



    @GetMapping("/addCoupon")
    public String addCoupon(@ModelAttribute ProductCoupon coupon){
        couponService.createCoupon(coupon);
        return "redirect:/ShowAddCoupons";
    }





    @GetMapping("/couponAdd")
    public String addCouponAdd(@ModelAttribute CouponDTO couponDTO,
                               @AuthenticationPrincipal(expression = "username") String username) {
        String coupon = couponDTO.getCoupon();



        useCouponService.discountProduct(coupon, username);
        return "redirect:/cartShow";
    }






    @GetMapping("/showCoupon")
    public String showCoupon(Model model){
        List<ProductCoupon> coupons = couponRepository.findAll()
                        .stream()
                                .filter(coupon -> !coupon.isDeleted())
                                        .toList();

        model.addAttribute("coupons", coupons);

        System.out.println(coupons);
          return "admin/showCoupons";

    }
















}
