package com.westernhills.westernhills.controller.shopPage;


import com.westernhills.westernhills.dto.CouponDTO;
import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.entity.userEntity.Cart;
import com.westernhills.westernhills.entity.userEntity.CheckOut;
import com.westernhills.westernhills.repo.CartRepository;
import com.westernhills.westernhills.repo.CheckOutRepository;
import com.westernhills.westernhills.repo.UserRepository;
import com.westernhills.westernhills.service.interfaceService.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class AddToCartController {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private CartService cartService;


    @Autowired
    private CartRepository cartRepository;



    @Autowired
    private CheckOutRepository checkOutRepository;



    @PostMapping("/addProductInCart")
    public String addCart(@RequestParam(name = "productId")UUID productId,
                          @AuthenticationPrincipal(expression = "username")String username
                           ){
        System.out.println(username);
        cartService.addToCartItem(username, productId);
        return "redirect:/findProducts";
    }


    @GetMapping("/addThoughProductInCart")
    public String addThoughCart(@RequestParam(name = "productId")UUID productId,
                          @AuthenticationPrincipal(expression = "username")String username){
        cartService.addToCartItem(username, productId);
        return "redirect:/";
    }



    @GetMapping("/cartShow")
    public String showCart(Model model,
                           @AuthenticationPrincipal(expression = "username")String username) {
        CouponDTO couponDTO = new CouponDTO();


        List<CheckOut> checkOuts=checkOutRepository.findByUser_Username(username);
        model.addAttribute("checkOuts",checkOuts);


        double amount = cartService.getTotalPrice(username);
        model.addAttribute("couponDTO", couponDTO);



        List<Cart> cartLists=cartService.getCartItems(username);
        model.addAttribute("cartList", cartLists);


        model.addAttribute("total", amount);
        return "userCart";
    }






    @GetMapping("/addQuantity")
    public String addProductQuantity(@RequestParam(name = "cartId") UUID cartId,
                                     @RequestParam(name="quantity") int quantity,
                                     @AuthenticationPrincipal(expression = "username") String username){

        cartService.addQuantity(username,cartId,quantity);
        return "redirect:/cartShow";
    }


    @GetMapping("/checkout")
    public String checkOut( @AuthenticationPrincipal(expression = "username") String username){
        List<Cart> cartList = cartService.getCartItems(username);

        List<Product> products = cartList.stream()
                .map(Cart::getProduct)
                .collect(Collectors.toList());
        return "redirect/cartShow";
    }




    @GetMapping("/removeCart/{id}")
    public String deleteUser(@PathVariable("id") UUID id) {
        cartRepository.deleteById(id);
        return "redirect:/cartShow";
    }







}
