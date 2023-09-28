package com.westernhills.westernhills.service;

import com.westernhills.westernhills.entity.admin.Banner;
import com.westernhills.westernhills.entity.admin.BannerImage;
import com.westernhills.westernhills.entity.admin.Image;
import com.westernhills.westernhills.repo.BannerRepository;
import com.westernhills.westernhills.service.interfaceService.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Service
public class BannerServiceImp implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;


    public static String uploadImage ="D:\\westernhills\\westernhills\\src\\main\\resources\\static\\all-Image";

    @Override
    public Banner addBanner(Banner banner, List<MultipartFile> files) throws IOException {

        Banner banner1= new Banner();
        banner1.setName(banner.getName());
        banner1.setDescription(banner.getDescription());


        List<BannerImage> bannerImages = new ArrayList<>();

        for(MultipartFile file : files){
            BannerImage bannerImage=new BannerImage();


            String bannerImageUUID= file.getContentType();
            Path filenameAndPath= Paths.get(uploadImage,bannerImageUUID);


            Files. write(filenameAndPath,file.getBytes());
            bannerImage.setFileName(bannerImageUUID);
            bannerImage.setBanner_id(banner1);
            bannerImages.add(bannerImage);

        }
        banner1.setBannerImages(bannerImages);
        bannerRepository.save(banner1);



        return null;
    }

    @Override
    public List<Banner> findAll() {
        return bannerRepository.findAll();
    }


}