package com.westernhills.westernhills.controller.admincontroller;


import com.westernhills.westernhills.dto.ResetPassDto;
import com.westernhills.westernhills.service.interfaceService.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;



    @GetMapping("/resetPassword")
    public String resetPasswordEmail(){
        return "Reset-password-email";
    }


    @PostMapping("/resetPasswordOtp")
    public String getResetPassword(@ModelAttribute ResetPassDto resetPassDto){
        passwordResetService.resetPass(resetPassDto);
        return "Reset-otp";
    }





    @PostMapping("/inputOtp")
    public String inputResetOtp(@ModelAttribute ResetPassDto resetPassDto){
        System.out.println("it is entered");

        boolean res= passwordResetService.verifyEmail(resetPassDto);
        if (res){

            return "resetPassword";
        }
        else {

           return "Reset-otp";
        }
    }





    @PostMapping("/updatePassword")
    public String updatePassword(@ModelAttribute ResetPassDto resetPassDto){
        passwordResetService.passwordUpdate(resetPassDto);
        return "login";
    }









}
