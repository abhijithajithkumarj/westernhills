package com.westernhills.westernhills.controller.shopPage;


import com.westernhills.westernhills.entity.admin.UseCoupon;
import com.westernhills.westernhills.repo.CartRepository;
import com.westernhills.westernhills.repo.CheckOutRepository;
import com.westernhills.westernhills.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class UseCouponController {


    @Autowired
    private CartService cartService;



    @Autowired
    private CheckOutRepository checkOutRepository;





}
