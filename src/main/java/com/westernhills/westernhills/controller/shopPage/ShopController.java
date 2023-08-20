package com.westernhills.westernhills.controller.shopPage;


import com.westernhills.westernhills.entity.admin.Product;
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

    @GetMapping("/findProducts")
    public String showProduct(Model model) {
        List<Product> products = productService.getAllProducts()
                        .stream()
                                .filter(product -> !product.isDeleted())
                                     .collect(Collectors.toList());
        model.addAttribute("products",products);
        return "productShop";
    }



    @GetMapping("/productDetail/{uuid}")
    public String productDetails(@PathVariable UUID uuid, Model model) {

        Optional<Product> productOptional = productService.getProductById(uuid);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("product", product);
            return "admin/product-detail";

        } else {


            return "productNotFound";
        }
    }









}
