package com.westernhills.westernhills.controller.shopPage;


import com.westernhills.westernhills.entity.userEntity.UserAddress;
import com.westernhills.westernhills.service.AddressService;
import com.westernhills.westernhills.service.CartService;
import com.westernhills.westernhills.service.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class CheckOutController {

    @Autowired
    private CheckOutService checkOutService;

    @Autowired
    private CartService cartService;

    @Autowired
    private AddressService addressService;


    @GetMapping("/showCheckout")
    public String customerProfile(Model model) {
        List<UserAddress> checkBox = addressService.findAll()
                        .stream()
                                .filter(addressService ->!addressService.isDeleted())
                                        .collect(Collectors.toList());
        model.addAttribute("checkBox", checkBox);
        return "projetdemo";
    }






    @GetMapping("/checkoutProduct")
    public String checkOutTheOrder(Model model,
                                   @RequestParam(name = "userAddress") UUID userAddressId,
                                   @AuthenticationPrincipal(expression = "username") String username){
        checkOutService.getCartItems(username, userAddressId);

        return "redirect:/successPage";

    }


    @GetMapping("/successPage")
    public String successPage(){
        return "admin/success";
    }


}
