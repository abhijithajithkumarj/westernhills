package com.westernhills.westernhills.controller.shopPage;


import com.westernhills.westernhills.entity.userEntity.UserAddress;
import com.westernhills.westernhills.repo.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProfileController {

    @Autowired
    private AddressRepository addressRepository;




    @GetMapping("/showCartPages")
    public String getCart(){
        return "redirect:/cartShow";

    }



    @GetMapping("/showWishlistPages")
    public String getWish(){
        return "redirect:/wishlist";

    }

    @GetMapping("/showPaymentPages")
    public String getPayment(){
        return "redirect:/showCheckout";

    }



    @GetMapping("/showHomePages")
    public String getHome(){
        return "redirect:/";
    }



    @GetMapping("/showProductsPages")
    public String getShopPage(){
        return "redirect:/findProducts";
    }



    @GetMapping("/showAddressPages")
    public String getAddressPage(Model model,
                                 @AuthenticationPrincipal(expression = "username") String username){
        List<UserAddress> userAddress=addressRepository.findByUser_Username(username)
                .stream()
                .filter(userAddress1 -> !userAddress1.isDeleted())
                .collect(Collectors.toList());
        model.addAttribute("userAddress",userAddress);
        return "addressPage";
    }




}
