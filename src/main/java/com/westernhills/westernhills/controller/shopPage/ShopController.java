package com.westernhills.westernhills.controller.shopPage;


import com.westernhills.westernhills.entity.admin.Category;
import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.repo.CategoryRepository;
import com.westernhills.westernhills.service.CategoryService;
import com.westernhills.westernhills.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class ShopController {




    @Autowired
    private ProductService productService;


    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private CategoryService categoryService;

    @GetMapping("/index")
    public String homePage(){
        return "index";
    }

    @GetMapping("/findProducts")
    public String showProduct(Model model) {
        List<Product> products = productService.getAllProducts()
                .stream()
                .filter(product -> !product.isDeleted())
                .collect(Collectors.toList());
        model.addAttribute("category",categoryService.getAllCategories());
        model.addAttribute("products",products);
        return "productShop";
    }




    @GetMapping("/findProduct/{id}")
    public String shopByCategory(Model model, @PathVariable UUID id) {
        List<Category> categories = categoryRepository.findAll();
        Optional<Category> selectedCategoryOptional = categoryService.getCategoryById(id);


        if (selectedCategoryOptional.isPresent()) {
            Category selectedCategory = selectedCategoryOptional.get();
            System.out.println(selectedCategory.getName());
            List<Product> products = productService.getAllProductsByCategoryId(id);



            model.addAttribute("categories", categories);
            model.addAttribute("selectedCategory", selectedCategory);
            model.addAttribute("products", products);
            return "productShop";
        } else {
            return "404";
        }
    }










    @GetMapping("/productDetail/{uuid}")
    public String productDetails(@PathVariable UUID uuid, Model model) {

        Optional<Product> productOptional = productService.getProductById(uuid);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("product", product);
            return "productShowAndDetils";

        } else {


            return "productNotFound";
        }
    }











}
