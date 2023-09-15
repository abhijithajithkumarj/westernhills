package com.westernhills.westernhills.controller.loginpage;


import com.westernhills.westernhills.dto.AuthReq;
import com.westernhills.westernhills.dto.CreateUserRequest;
import com.westernhills.westernhills.dto.OtpDto;
import com.westernhills.westernhills.entity.userEntity.User;
import com.westernhills.westernhills.repo.UserRepository;
import com.westernhills.westernhills.service.ProductService;
import com.westernhills.westernhills.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;


    @Autowired
    private UserRepository userRepository;



    @Autowired
    private ProductService productService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/")
    public String getHomePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null && !authentication.isAuthenticated()) {
            return "login";
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {


           return "Admin/adminpanel";

        }
        return "index";
    //        return "`productShowAndDetils`";
    }








    @GetMapping("/login")
    public String loginpage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }

        return "redirect:/";

    }







   @PostMapping("/userlogin")
   public String processLoginForm(@ModelAttribute("authreq") AuthReq authreq, Model model) {
    User user = userService.findByUsername(authreq.getUsername());

    if (user == null || ! user.isEnabled()) {
        model.addAttribute("error", "Your account is not enabled. Please contact the administrator.");
        return "login";
    }
    if (user.isDeleted()==false){
        System.out.println(user.isDeleted());
        return "login";

    }

    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(authreq.getUsername(), authreq.getPassword());


    try {
        Authentication authentication = authenticationManager.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        return "redirect:/";
    } catch (AuthenticationException e) {
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }








}






    @GetMapping("/signup")
    public String getSignUpPage(Model model) {
        model.addAttribute("signuprequest",new CreateUserRequest());
        return "signup";
    }



    @PostMapping("/signup")
    public String signup(@ModelAttribute("signuprequest") CreateUserRequest signUpRequest, Model model) {
        OtpDto otp=userService.createUser(signUpRequest);
        model.addAttribute("otp",otp);
            return "otpPage";

    }





    @PostMapping("/verifyotp")
    public String verifyAccount(@ModelAttribute("otp") OtpDto otp) {

        System.out.println(otp);
        boolean res=userService.verifyAccount(otp);


        if(res) {
            return "login";
        }else {

            return "otpPage";
        }


    }



    @GetMapping("/user-page")
    public String showUserPage(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin/user-page";
    }


    @GetMapping("/blockUser/{id}")
    public String blockUser(@PathVariable Long id){
        userService.blockUser(id);
        return "redirect:/user-page";

    }

    @GetMapping("/unBlockUser/{id}")
    public String UnBlockUser(@PathVariable Long id){
        userService.unBlockUser(id);
        return "redirect:/user-page";

    }















    @GetMapping("/logouts")
    public String handleLogoutRequest(HttpServletRequest request) {

        request.getSession().invalidate();

        String username = (String) request.getSession().getAttribute("username");

        return "redirect:/login?logout";

    }






}
