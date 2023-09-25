package com.westernhills.westernhills.controller.shopPage;


import com.westernhills.westernhills.Otp.ConfigReferenceCode;
import com.westernhills.westernhills.entity.userEntity.Cart;
import com.westernhills.westernhills.entity.userEntity.CheckOut;
import com.westernhills.westernhills.entity.userEntity.Referral;
import com.westernhills.westernhills.entity.userEntity.UserAddress;
import com.westernhills.westernhills.entity.wallet.Wallet;
import com.westernhills.westernhills.repo.AddressRepository;
import com.westernhills.westernhills.repo.CheckOutRepository;
import com.westernhills.westernhills.repo.ReferralRepository;
import com.westernhills.westernhills.service.interfaceService.CartService;
import com.westernhills.westernhills.service.interfaceService.ReferralService;
import com.westernhills.westernhills.service.walleteservice.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProfileController {

    @Autowired
    private AddressRepository addressRepository;



    @Autowired
    private WalletService walletService;



    @Autowired
    private CartService cartService;


    @Autowired
    private CheckOutRepository checkOutRepository;


    @Autowired
    private ConfigReferenceCode configReferenceCode;


    @Autowired
    private ReferralService referralService;





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


    @GetMapping("/customers")
    public String customerProfile(Model model , @AuthenticationPrincipal(expression = "username") String username) {



        List<UserAddress> userAddresses = addressRepository.findByUser_Username(username)
                .stream()
                .filter(address -> !address.isDeleted())
                .collect(Collectors.toList());





        double wallets = walletService.walletTotalPayment(username);
        System.out.println(wallets);
        model.addAttribute("wallet", wallets);


        List<CheckOut> checkOutList = checkOutRepository.findByUser_Username(username);
        model.addAttribute("checkOutList", checkOutList);


            List<Cart> cartLists=cartService.getCartItems(username);
            model.addAttribute("cartList", cartLists);


            List<Wallet> wishList=walletService.findByUser_Username(username);
            model.addAttribute("walletList", wishList);



        double total=cartService.getTotalPrice(username);
        model.addAttribute("userAddress", userAddresses);


        model.addAttribute("total",total);
        return "profilePageMain";


    }


    @GetMapping("/generateReferralCode")
    public String referralCode(Model model,
                               @AuthenticationPrincipal(expression = "username") String username){
        String referralCode=configReferenceCode.generateCouponCode();
        referralService.SaveReferralCode(referralCode, username);
        model.addAttribute("referralCode", referralCode);
        return "profilePageMain";


    }


    @PostMapping("/referralCode")
    public String ReferrerCode(Model model, @RequestParam String referralCode ,
                               @AuthenticationPrincipal(expression = "username") String username){
        referralService.setReferralsCodeDiscount(referralCode, username);
        System.out.println(referralCode);
        return "profilePageMain";

    }













}
