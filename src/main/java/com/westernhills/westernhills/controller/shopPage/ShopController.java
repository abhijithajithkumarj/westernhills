package com.westernhills.westernhills.controller.shopPage;


import com.westernhills.westernhills.entity.admin.Banner;
import com.westernhills.westernhills.entity.admin.Category;
import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.repo.CategoryRepository;
import com.westernhills.westernhills.service.interfaceService.BannerService;
import com.westernhills.westernhills.service.interfaceService.CategoryService;
import com.westernhills.westernhills.service.interfaceService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class ShopController {




    @Autowired
    private ProductService productService;


    @Autowired
    private BannerService bannerService;


    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private CategoryService categoryService;

    @GetMapping("/index")
    public String homePage(){
        return "redirect:/";
    }

    @GetMapping("/findProducts")
    public String showProduct(Model model) {
        List<Product> products = productService.getAllProducts()
                .stream()
                .filter(product -> !product.isDeleted())
                .collect(Collectors.toList());

        List<Banner> banner=bannerService.findAll()
                        .stream()
                        .filter(banner1 -> !banner1.isDeleted())
                        .collect(Collectors.toList());

        model.addAttribute("category",categoryService.getAllCategories());
        System.out.println(categoryService.getAllCategories());
        model.addAttribute("products",products);

            model.addAttribute("banner" , banner);
        return "productShop";
    }














    @GetMapping("/findProduct/{id}")
    public String shopByCategory(Model model, @PathVariable UUID id) {
        List<Category> categories = categoryRepository.findAll();
        Optional<Category> selectedCategoryOptional = categoryService.getCategoryById(id);


        if (selectedCategoryOptional.isPresent()) {
            Category selectedCategory = selectedCategoryOptional.get();



            List<Product> products = productService.getAllProductsByCategoryId(id)
                            .stream()
                                    .filter(product -> !product.isDeleted())
                                            .collect(Collectors.toList());

            model.addAttribute("categories", categories);

            model.addAttribute("selectedCategory", selectedCategory);


            model.addAttribute("products", products);
            return "productShop";
        } else {
            return "404";
        }
    }












    @GetMapping("/searchCategory")
    public String searchCategories(@RequestParam String searchCategory, Model model) {

        Optional<Category> selectedCategoryOptional = categoryService.findByName(searchCategory);

        System.out.println("fine");
        if (selectedCategoryOptional.isPresent()) {
            Category selectedCategory = selectedCategoryOptional.get();


            List<Product> products = productService.getAllProductsByCategoryId(selectedCategory.getId());


            model.addAttribute("selectedCategory", selectedCategory);

            model.addAttribute("products", products);


        } else {

            return "404";
        }

        return "productShop";
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
