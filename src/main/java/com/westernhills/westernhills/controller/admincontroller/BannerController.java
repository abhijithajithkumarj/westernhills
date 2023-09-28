package com.westernhills.westernhills.controller.admincontroller;


import com.westernhills.westernhills.dto.ProductDto;
import com.westernhills.westernhills.entity.admin.Banner;
import com.westernhills.westernhills.service.interfaceService.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class BannerController {


    @Autowired
    private BannerService bannerService;


    @GetMapping("/showBanner")
    public  String showBanner(Model model){
        return "admin/bannerPage";
    }


    @PostMapping("/addBanner")
    public String setBanner(@ModelAttribute("banner") Banner banner, Model model,
                            @RequestParam("files") List<MultipartFile> files) throws IOException {
        bannerService.addBanner(banner,files);

        model.addAttribute("banner",bannerService.findAll());
        return "redirect:/showBanner";


    }





}
