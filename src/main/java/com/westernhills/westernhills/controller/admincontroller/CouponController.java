package com.westernhills.westernhills.controller.admincontroller;


import com.westernhills.westernhills.entity.admin.Category;
import com.westernhills.westernhills.entity.admin.CategoryCoupon;
import com.westernhills.westernhills.entity.admin.ProductCoupon;
import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.repo.CategoryRepository;
import com.westernhills.westernhills.repo.CouponRepository;
import com.westernhills.westernhills.repo.ProductRepository;
import com.westernhills.westernhills.service.coupon.CategoryCouponService;
import com.westernhills.westernhills.service.coupon.CouponService;
import com.westernhills.westernhills.service.coupon.UseCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CouponController {


    @Autowired
    private CouponRepository couponRepository;




    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private UseCouponService useCouponService;



    @Autowired
    private CouponService couponService;

    @GetMapping("/ShowAddCoupons")
    public String showAddCoupon(Model model) {
        model.addAttribute("coupon", new ProductCoupon());
        List<Product> products=productRepository.findAll()
                .stream()
                .filter(product -> !product.isDeleted())
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        System.out.println(products);
        return "admin/Coupon-add";
    }






    @GetMapping("/addCoupon")
    public String addCoupon(@ModelAttribute ProductCoupon coupon){
        System.out.println("hii");
        couponService.createCoupon(coupon);
        return "redirect:/ShowAddCoupons";
    }








    @GetMapping("/showCoupon")
    public String showCoupon(Model model){
        List<ProductCoupon> coupons = couponRepository.findAll()
                        .stream()
                                .filter(coupon -> !coupon.isDeleted())
                                        .collect(Collectors.toList());

        model.addAttribute("coupons", coupons);

        System.out.println(coupons);
          return "admin/showCoupons";

    }
















}
