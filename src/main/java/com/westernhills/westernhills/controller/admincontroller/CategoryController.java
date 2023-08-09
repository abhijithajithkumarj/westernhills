package com.westernhills.westernhills.controller.admincontroller;


import com.westernhills.westernhills.entity.User;
import com.westernhills.westernhills.entity.admin.Category;
import com.westernhills.westernhills.repo.CategoryRepository;
import com.westernhills.westernhills.repo.UserRepository;
import com.westernhills.westernhills.service.CategoryService;
import com.westernhills.westernhills.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;


@Controller
public class CategoryController {
    @Autowired
    private UserService userService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;



    @Autowired
    private CategoryService categoryService;






    @GetMapping("/category")
    public String  category(Model model) {
        model.addAttribute("categoryNew",new Category());
        List<Category> categories = categoryService.findAll()
                .stream()
                .filter(category -> !category.isDeleted())
                .toList();
        model.addAttribute("categories",categories);
        model.addAttribute("size",categories.size());
        model.addAttribute("title","categorise");
        return "admin/category";

    }




    @PostMapping("/add-category")
    public String addCategory(@ModelAttribute("categoryNew") Category Category, RedirectAttributes redirectAttributes) {
        try {
            categoryService.save(Category);
            redirectAttributes.addFlashAttribute("success","Add successfully");
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return "redirect:/category";

    }






    @GetMapping("/delete-category/{id}")
    public String deleteProduct(@PathVariable UUID id, RedirectAttributes attributes) {
       categoryService.deleteById(id);
        return "redirect:/showProduct";
    }




    @PostMapping("/editCategory")
    public String editCategory(@ModelAttribute("category") Category category) {
        System.out.println("shdfguioahguiohwuihweriopghweriputgipwqrutgiwurtgiqwgt");


        categoryService.updateCategory(category.getId(), category);
        return "admin/update";

    }


































//    @GetMapping("/user-page/search")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public String searchUsersByUsername(@RequestParam String searchTerm, Model model) {
//        List<User> users= userRepository.findByUsername(searchTerm).stream()
//                .collect(Collectors.toList());
//        model.addAttribute("users",users);
//        return "/adminpanel";
//    }
//
//
//
//
//
//    @GetMapping("/adminpanel/edit/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public String editUserForm(@PathVariable("id") Long id,Model model) {
//
//        User user=userRepository.findById(id).orElseThrow();
//        model.addAttribute("user", user);
//        return "updateuser";
//    }
//
//
//
//
//    @PostMapping("/adminpanel/update")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public String updateUser(@ModelAttribute("user") User updatedUser) {
//        userService.upadateUser(updatedUser.getId(),updatedUser);
//        return "redirect:/adminpanel";
//    }








}
