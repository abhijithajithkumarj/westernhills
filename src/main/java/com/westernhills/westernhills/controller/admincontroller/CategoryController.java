package com.westernhills.westernhills.controller.admincontroller;


import com.westernhills.westernhills.entity.admin.Category;
import com.westernhills.westernhills.repo.CategoryRepository;
import com.westernhills.westernhills.repo.UserRepository;
import com.westernhills.westernhills.service.interfaceService.CategoryService;
import com.westernhills.westernhills.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


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
                .collect(Collectors.toList());
        model.addAttribute("categories",categories);
        model.addAttribute("size",categories.size());
        model.addAttribute("title","categorise");
        return "category";

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
        return "redirect:/category";
    }




    @PostMapping("/editCategory")
    public String editCategory(@ModelAttribute("category") Category category) {
        categoryService.updateCategory(category.getId(), category);
        return "update";

    }



























}
