package com.westernhills.westernhills.controller.shopPage;


import com.razorpay.Order;
import com.razorpay.RazorpayException;
import com.westernhills.westernhills.entity.userEntity.Cart;
import com.westernhills.westernhills.entity.userEntity.PaymentMethod;
import com.westernhills.westernhills.entity.userEntity.UserAddress;
import com.westernhills.westernhills.repo.CartRepository;
import com.westernhills.westernhills.service.RazorpayService;
import com.westernhills.westernhills.service.AddressService;
import com.westernhills.westernhills.service.CartService;
import com.westernhills.westernhills.service.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private CartRepository cartRepository;




    @Autowired
    private RazorpayService razorpayService;


    private static String CURRENCY="INR";


    @GetMapping("/showCheckout")
    public String customerProfile(Model model)
    throws RazorpayException{
        List<UserAddress> checkBox = addressService.findAll()
                        .stream()
                                .filter(addressService ->!addressService.isDeleted())
                                        .collect(Collectors.toList());

        model.addAttribute("checkBox", checkBox);



//        return "checkout";
        return "projectdemo";
    }











    @GetMapping("/checkoutProduct")
    public String checkOutTheOrder(
            Model model,
            @RequestParam(name = "userAddress") UUID userAddressId,
            @RequestParam(name = "paymentMethod") PaymentMethod paymentMethod,
            @AuthenticationPrincipal(expression = "username") String username) throws RazorpayException {

        List<Cart> cartItems = cartRepository.findByUser_Username(username);
        double cartTotal=cartService.getTotalPrice(username);


        System.out.println(paymentMethod);
        System.out.println(userAddressId);


        if (paymentMethod==PaymentMethod.COD){
            checkOutService.getCartItemsAll(username, userAddressId);
        } else if (paymentMethod==PaymentMethod.ONLINE) {
            System.out.println("successfully");

        }


        return "redirect:/showCheckout";
    }



    @GetMapping("/successPage")
    public String successPage(){

        return "admin/success";
    }


}
