package com.westernhills.westernhills.controller.shopPage;


import com.razorpay.RazorpayException;
import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.entity.userEntity.Cart;
import com.westernhills.westernhills.entity.userEntity.PaymentMethod;
import com.westernhills.westernhills.entity.userEntity.UserAddress;
import com.westernhills.westernhills.repo.AddressRepository;
import com.westernhills.westernhills.repo.CartRepository;
import com.westernhills.westernhills.repo.ProductRepository;
import com.westernhills.westernhills.service.RazorpayService;
import com.westernhills.westernhills.service.interfaceService.AddressService;
import com.westernhills.westernhills.service.interfaceService.CartService;
import com.westernhills.westernhills.service.interfaceService.CheckOutService;
import com.westernhills.westernhills.service.walleteservice.WalletService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    private AddressRepository addressRepository;


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RazorpayService razorpayService;


    @Autowired
    private WalletService walletService;


    private static String CURRENCY="INR";


    @GetMapping("/showCheckout")
    public String customerProfile(Model model, @AuthenticationPrincipal(expression = "username") String username) {



            List<UserAddress> userAddresses = addressRepository.findByUser_Username(username)
                    .stream()
                    .filter(address -> !address.isDeleted())
                    .collect(Collectors.toList());

            List<Product> products = productRepository.findAll()
                    .stream()
                    .filter(product -> !product.isDeleted())
                    .collect(Collectors.toList());


            List<Cart> cartList= cartRepository.findByUser_Username(username)
                            .stream()
                                    .filter(cart -> !cart.isDeleted())
                                            .collect(Collectors.toList());


            model.addAttribute("products", products);
            model.addAttribute("userAddresses", userAddresses);
            model.addAttribute("cartList", cartList);
            return "advuser/onlinepayment";

    }


    @GetMapping("/addQuantityInPaymentSide")
    public String addProductQuantity(@RequestParam(name = "cartId") UUID cartId,
                                     @RequestParam(name="quantity") int quantity,
                                     @AuthenticationPrincipal(expression = "username") String username){
        cartService.addQuantity(username,cartId,quantity);
        return "redirect:/showCheckout";
    }



    @GetMapping("/checkoutProduct")
    @ResponseBody
    public String checkOutTheOrder(
            Model model,
            @RequestParam(name = "userAddress") UUID userAddressId,
            @RequestParam(name = "paymentMethod") PaymentMethod paymentMethod,
            @AuthenticationPrincipal(expression = "username") String username) throws RazorpayException {



        List<Cart> checking = cartRepository.findByUser_Username(username);
        System.out.println(checking);



        double amount = cartService.getTotalPriceAddDiscount(username);
        double wallet= walletService.walletTotalPayment(username);
        System.out.println(wallet);

        com.razorpay.Order order = null;
        String orderId = "";

        if (paymentMethod == PaymentMethod.COD && amount!=0) {
            checkOutService.getCartItemsAll(username, userAddressId);
        } else if(paymentMethod == PaymentMethod.ONLINE && !checking.isEmpty() ){

            order = razorpayService.createOrder(amount, CURRENCY);
            orderId = order.get("id").toString();
        }else {
            System.out.println("payment method is not supported");
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("orderId", orderId);
        jsonResponse.put("amount", amount);
        jsonResponse.put("message", "Redirecting to /showCheckout");


        return jsonResponse.toString();
    }





    @PostMapping("/placeOrder")
    @ResponseBody
    public String handlePaymentCallback(@RequestBody Map<String, Object> callbackData,
                                        @AuthenticationPrincipal(expression = "username") String username) {
        System.out.println("Received Callback Data: " + callbackData.toString());

        String userAddressStr = (String) callbackData.get("shippingAddress");

        UUID userAddress = null;
        System.out.println(userAddressStr);
        try {
            userAddress = UUID.fromString(userAddressStr);
        } catch (IllegalArgumentException ignored) {

        }

        if (userAddress != null) {
            checkOutService.getOnlineCheckout(username, userAddress);
        }

        return "redirect:/";
    }






    @GetMapping("/showOnlineCheckout")
    public String showOnlinePaymentMethod(Model model,
                                          @AuthenticationPrincipal(expression = "username") String username
                                          ) throws RazorpayException {
        double amount = cartService.getTotalPrice(username); // Calculate the total amount
        List<Cart> cartItems = cartRepository.findByUser_Username(username);
        double cartTotal = cartService.getTotalPrice(username);

        razorpayService.createOrder(amount, CURRENCY);



        com.razorpay.Order order=razorpayService.createOrder(amount,CURRENCY);
        String orderId = order.get("id").toString();

        System.out.println(orderId);
        List<UserAddress> checkBox = addressService.findAll()
                .stream()
                .filter(addressService ->!addressService.isDeleted())
                .collect(Collectors.toList());




        model.addAttribute("orderId", orderId);
        model.addAttribute("checkBox", checkBox);
        return "advuser/onlinepayment";

    }


    @GetMapping("/myorders")
    public String getSuccessfulPage(){
        return "advuser/paymentSuccesfulPage";

    }







    @GetMapping("/successPage")
    public String successPage(){

        return "Admin/success";
    }


}
