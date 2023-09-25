package com.westernhills.westernhills.controller.shopPage;


import com.westernhills.westernhills.entity.userEntity.Wishlist;
import com.westernhills.westernhills.repo.ProductRepository;
import com.westernhills.westernhills.service.interfaceService.ProductService;
import com.westernhills.westernhills.service.interfaceService.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Controller
public class WishlistController {



    @Autowired
    private WishlistService wishlistService;


    @Autowired
    private ProductService productService;




    @Autowired
    private ProductRepository productRepository;




    @GetMapping("/wishlist")
    public String showList(Model model,
                           @AuthenticationPrincipal(expression = "username") String username){
        List<Wishlist> wishlist = wishlistService.wishlistProduct(username);
        List<Wishlist> wishlistProduct = wishlistService.wishlistFindByUserName(username);
        model.addAttribute("wishlistProduct", wishlistProduct);
        model.addAttribute("wishlist", wishlist);
        return "wishlist";

    }



    @GetMapping("/addWishlist/{id}")
    public String addWishList(Model model,
                              @PathVariable UUID id,
                              @AuthenticationPrincipal(expression = "username") String username){
        wishlistService.addToWishlist(username,id);
        return "redirect:/findProducts";

    }


    @GetMapping("/removeWishlist/{id}")
    public String deleteUser(@PathVariable("id") UUID id) {
        wishlistService.deleteWishlist(id);
        return "redirect:/cartShow";
    }








}
