package com.westernhills.westernhills.controller.shopPage;


import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.repo.ProductRepository;
import com.westernhills.westernhills.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ShopController {




    @Autowired
    private ProductService productService;

    @GetMapping("/findProducts")
    public String showProduct(Model model) {
        List<Product> products = productService.getAllProducts()
                        .stream()
                                .filter(product -> !product.isDeleted())
                                        .toList();
        model.addAttribute("products",products);
        return "productShop";
    }



    @GetMapping("/productDetail/{uuid}")
    public String productDetails(@PathVariable UUID uuid, Model model) {

        Optional<Product> productOptional = productService.getProductById(uuid);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("product", product);  // Add the individual product to the model
            return "product-detail";
        } else {


            return "productNotFound";
        }
    }

//    @GetMapping("/productDetail")
//    public String getProduct(){
//        return "product-detail";
//    }







}
