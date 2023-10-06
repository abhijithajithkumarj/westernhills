package com.westernhills.westernhills.controller.admincontroller;


import com.westernhills.westernhills.entity.admin.Category;
import com.westernhills.westernhills.entity.admin.CategoryCoupon;
import com.westernhills.westernhills.entity.admin.ProductCoupon;
import com.westernhills.westernhills.repo.CategoryRepository;
import com.westernhills.westernhills.service.coupon.CategoryCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;





@Controller
public class CategoryCouponController {

    @Autowired
    private CategoryRepository categoryRepository;



    @Autowired
    private CategoryCouponService categoryCouponService;





    @GetMapping("/ShowCategoryCoupons")
    public String showAddCategory(Model model) {
        model.addAttribute("coupon", new CategoryCoupon());
        List<Category> category=categoryRepository.findAll()
                .stream()
                .filter(category1 -> !category1.isDeleted())
                .toList();
        model.addAttribute("category", category);
        System.out.println(category);
        return "Admin/CategoryCoupon";
    }



    @GetMapping("/CategoryCoupons")
    public String addCouponCategoryCoupon(@ModelAttribute CategoryCoupon coupon){
        categoryCouponService.createCoupon(coupon);
        return "redirect:/ShowAddCoupons";
    }






}
