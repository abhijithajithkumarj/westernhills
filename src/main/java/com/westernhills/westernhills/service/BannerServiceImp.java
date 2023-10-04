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
import java.util.Optional;
import java.util.UUID;


@Service
public class BannerServiceImp implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;


    public static String uploadImage ="D:\\westernhills\\westernhills\\src\\main\\resources\\static\\banner-image";

//    @Override
//    public Banner addBanner(Banner banner, List<MultipartFile> files) throws IOException {
//
//        List<Banner> bannerOptional =bannerRepository.findAll();
//
//
//        Banner banner1 = new Banner();
//
//
//
////
////        if (banner1.){
////            Banner existingDefaultBanner= bannerOptional.stream()
////                    .filter(Banner::isDefaultBanner)
////
////        }
//
//
//        List<BannerImage> bannerImages = new ArrayList<>();
//
//        for (MultipartFile file : files) {
//            BannerImage bannerImage = new BannerImage();
//
//            String originalFilename = file.getOriginalFilename();
//            String fileExtension = "";
//
//
//            int lastIndex = originalFilename.lastIndexOf('.');
//            if (lastIndex >= 0) {
//                fileExtension = originalFilename.substring(lastIndex + 1);
//            }
//
//
//            String uniqueFilename = UUID.randomUUID().toString() + "." + fileExtension;
//            Path filenameAndPath = Paths.get(uploadImage, uniqueFilename);
//            Files.createDirectories(filenameAndPath.getParent());
//
//
//            Files.write(filenameAndPath, file.getBytes());
//
//            bannerImage.setFileName(uniqueFilename);
//            bannerImage.setBanner_id(banner1);
//            bannerImages.add(bannerImage);
//
//
//
//
//        }
//
//        banner1.setBannerImages(bannerImages);
//        bannerRepository.save(banner1);
//
//        return null;
//    }




    @Override
    public Banner addBanner(Banner banner, List<MultipartFile> files) throws IOException {

             List<Banner> bannerOptional = bannerRepository.findAll();

             Banner banner1 = new Banner();




            banner1.setName(banner.getName());
            banner1.setDescription(banner.getDescription());
            banner1.setPlacement(banner.getPlacement());
            banner1.setDefaultBanner(true);






            Banner bannerDefault = bannerOptional.stream()
                        .filter(banner2 -> banner2.isDefaultBanner() &&
                                banner2.getPlacement() == banner.getPlacement())
                        .findFirst()
                        .orElse(null);


            if (bannerDefault != null) {
                    bannerDefault.setDefaultBanner(false);
                    bannerRepository.save(bannerDefault);

            }

            List<BannerImage> bannerImages = new ArrayList<>();

        for (MultipartFile file : files) {
            BannerImage bannerImage = new BannerImage();

            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";


            int lastIndex = originalFilename.lastIndexOf('.');
            if (lastIndex >= 0) {
                fileExtension = originalFilename.substring(lastIndex + 1);
            }


            String uniqueFilename = UUID.randomUUID().toString() + "." + fileExtension;
            Path filenameAndPath = Paths.get(uploadImage, uniqueFilename);
            Files.createDirectories(filenameAndPath.getParent());


            Files.write(filenameAndPath, file.getBytes());

            bannerImage.setFileName(uniqueFilename);
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
