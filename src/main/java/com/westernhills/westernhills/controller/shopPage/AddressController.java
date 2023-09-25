package com.westernhills.westernhills.controller.shopPage;
import com.westernhills.westernhills.entity.userEntity.CheckOut;
import com.westernhills.westernhills.entity.userEntity.User;
import com.westernhills.westernhills.entity.userEntity.UserAddress;
import com.westernhills.westernhills.repo.AddressRepository;
import com.westernhills.westernhills.repo.CheckOutRepository;
import com.westernhills.westernhills.service.interfaceService.AddressService;
import com.westernhills.westernhills.service.interfaceService.CartService;
import com.westernhills.westernhills.service.UserService;
import com.westernhills.westernhills.service.walleteservice.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

    @Controller
    public class AddressController {

        @Autowired
        private AddressService addressService;


        @Autowired
        private AddressRepository addressRepository;

        @Autowired
        private UserService userService;


        @Autowired
        private CartService cartService;



        @Autowired
        private CheckOutRepository checkOutRepository;



        @Autowired
        private WalletService walletService;













        @GetMapping("/customerOrder")
        public String customerOrder(Model model,@AuthenticationPrincipal(expression = "username") String username){
            List<CheckOut> ListTheOrder=checkOutRepository.findByUser_Username(username)
                    .stream()
                    .filter(checkOut -> !checkOut.isDeleted())
                    .collect(Collectors.toList());
            model.addAttribute("checkOutOrder", ListTheOrder);
            return "User-order";

        }




        @GetMapping("/addAddress")
        public String addAddress(){
            return "addressPage";
        }







        @PostMapping("/userAddressSave")
        public String addressSave(@ModelAttribute UserAddress userAddress){
            System.out.println(userAddress);

            User user=userService.getUserName(getCurrentUser());
            userAddress.setUser(user);

            List<UserAddress> userAddressList=addressService.findByUser(user);

            if (userAddressList.isEmpty()){
                userAddress.setDefaultAddress(true);
                addressService.save(userAddress);
                return "redirect:/customers";

            }else{
                if (userAddress.isDefaultAddress()){
                    UserAddress existingDefaultAddress=userAddressList.stream()
                            .filter(UserAddress::isDefaultAddress)
                            .findFirst()
                            .orElse(null);


                    if (existingDefaultAddress !=null){
                        existingDefaultAddress.setDefaultAddress(false);
                        addressService.save(existingDefaultAddress);
                    }
                    addressService.save(userAddress);
                }


            }
            return "redirect:/customers";
        }




       @PostMapping("/deleteAddress")
       public String delete(@RequestParam("addressId") UUID id){
        addressService.disableAddress(id);
        return "redirect:/showAddressPages";
       }



        @GetMapping("/editeAddress/{id}")
        public String editAddress(@PathVariable("id") UUID id, Model model) {
            UserAddress userAddress = addressRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid address ID: " + id));
            model.addAttribute("userAddress", userAddress);
            return "addressEdit";
        }




        @PostMapping("/updateAddress")
        public String updateUser(@ModelAttribute("user") UserAddress updatedUser) {

            
            addressService.updateAddress(updatedUser.getId(),updatedUser);
            return "redirect:/addAddress";
        }


        @GetMapping("/updateUserInPaymentPage/{id}")
        public String updateUserInPaymentPage(@PathVariable("id") UUID id, Model model){
            UserAddress userAddress = addressRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid address ID: " + id));
            model.addAttribute("userAddress", userAddress);
            return "addressEdit";

        }

        @PostMapping("/updateUserInPaymentPageFix")
        public String updateUserInPaymentPageFix(@ModelAttribute("user") UserAddress updatedUser) {
            addressService.updateAddress(updatedUser.getId(),updatedUser);
            return "advuser/onlinepayment";
        }







        public String getCurrentUser() {
           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           return authentication.getName();

       }





    }
