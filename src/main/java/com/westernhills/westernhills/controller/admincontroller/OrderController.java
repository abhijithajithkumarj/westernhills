package com.westernhills.westernhills.controller.admincontroller;


import com.westernhills.westernhills.entity.userEntity.CheckOut;
import com.westernhills.westernhills.repo.CheckOutRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OrderController {




    @Autowired
    private CheckOutRepository checkOutRepository;



    @GetMapping("/showAllOrder")
    public String showOrders(Model model){
        List<CheckOut> checkOuts =checkOutRepository.findAll();
        model.addAttribute("checkout",checkOuts);
        return "admin/Order";
    }




}
