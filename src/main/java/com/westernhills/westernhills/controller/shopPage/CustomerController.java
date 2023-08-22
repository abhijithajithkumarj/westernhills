package com.westernhills.westernhills.controller.shopPage;
import com.westernhills.westernhills.entity.userEntity.User;
import com.westernhills.westernhills.entity.userEntity.UserAddress;
import com.westernhills.westernhills.repo.AddressRepository;
import com.westernhills.westernhills.service.AddressService;
import com.westernhills.westernhills.service.UserService;
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
    public class CustomerController {

        @Autowired
        private AddressService addressService;


        @Autowired
        private AddressRepository addressRepository;

        @Autowired
        private UserService userService;

        @GetMapping("/customers")
        public String customerProfile(Model model , @AuthenticationPrincipal(expression = "username") String username) {
            List<UserAddress> userAddresses = addressRepository.findByUser_Username(username)
                    .stream()
                    .filter(address -> !address.isDeleted())
                    .collect(Collectors.toList());
            System.out.println(userAddresses);
            model.addAttribute("userAddress", userAddresses);
            return "User-profile";
        }


        @GetMapping("/addAddress")
        public String addAddress(){
            return "User-address";
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
        return "redirect:/customers";
    }








        public String getCurrentUser(){
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            return authentication.getName();

        }







}
