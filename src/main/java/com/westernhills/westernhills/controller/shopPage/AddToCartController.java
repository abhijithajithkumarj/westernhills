package com.westernhills.westernhills.controller.shopPage;


import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.entity.userEntity.Cart;
import com.westernhills.westernhills.entity.userEntity.User;
import com.westernhills.westernhills.repo.CartRepository;
import com.westernhills.westernhills.repo.UserRepository;
import com.westernhills.westernhills.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class AddToCartController {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private CartService cartService;


    @Autowired
    private CartRepository cartRepository;



    @PostMapping("/addProductInCart")
    public String addCart(@RequestParam(name = "productId")UUID productId,
                          @AuthenticationPrincipal(expression = "username")String username

                           ){
        System.out.println(username);

        cartService.addToCartItem(username, productId);
        return "redirect:/findProducts";
    }



    @GetMapping("/cartShow")
    public String showCart(Model model){
        double total=cartService.totalPrice();
        List<Cart> cartList=cartRepository.findAll()
                        .stream()
                                .filter(cart -> !cart.isDeleted())
                                        .toList();
        model.addAttribute("cartList",cartList);
        System.out.println(total);
        model.addAttribute("total",total);
        return "User-cart";
    }


    @GetMapping("/addQuantity")
    public String addProductQuantity(@RequestParam(name = "cartId") UUID cartId,
                                     @RequestParam(name="quantity") int quantity,
                                     @AuthenticationPrincipal(expression = "username") String username){
        cartService.addQuantity(username,cartId,quantity);
        return "redirect:/cartShow";

    }








}
