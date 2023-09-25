package com.westernhills.westernhills.controller.admincontroller.offerpage;


import com.westernhills.westernhills.entity.admin.Category;
import com.westernhills.westernhills.entity.admin.Offer;
import com.westernhills.westernhills.repo.CategoryRepository;
import com.westernhills.westernhills.repo.OfferRepository;
import com.westernhills.westernhills.repo.ProductRepository;
import com.westernhills.westernhills.service.interfaceService.CategoryService;
import com.westernhills.westernhills.service.interfaceService.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class                                             OfferController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private CategoryService categoryService;


    @Autowired
    private OfferRepository offerRepository;


    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private CategoryRepository categoryRepository;




    @GetMapping("/offerMakingPage")
    public String OfferMakingPage(Model model) {
        List<Category> categories=categoryRepository.findAll()
                .stream()
                .filter(category -> !category.isDeleted())
                .collect(Collectors.toList());

        model.addAttribute("categories", categories);

        List<Offer> offers= offerRepository.findAll()
                .stream()
                .filter(offer -> !offer.isDeleted())
                .toList();
        model.addAttribute("offer",offers);



        Offer offer = new Offer();
        return "admin/OfferMaking";
    }






    @GetMapping("/addOffer")
    public String OfferAdd( @ModelAttribute Offer offer,Model model) {

        Optional<Offer> userOffer = offerService.findbyCategory(offer.getCategory().getName());

        if(userOffer.isPresent()){
            model.addAttribute("user","Offer already exists.");
        }else{
            offerService.offerSetting(offer);

        }



        return "redirect:/offerMakingPage";

    }


}

