package com.westernhills.westernhills.util;


import com.westernhills.westernhills.entity.admin.Image;
import com.westernhills.westernhills.entity.admin.Product;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageUpload {

    private Product product;

    private final String UPLOAD_FOLDER = "D:\\westernhills\\westernhills\\src\\main\\resources\\static\\images\\image-save";

    public boolean uploadImage(MultipartFile imageProduct) {
        boolean isUpload = false;
        try {

            Path targetPath = Paths.get(UPLOAD_FOLDER, imageProduct.getOriginalFilename());
            Files.copy(imageProduct.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            Image image = new Image();

            image.setFileName(product.getUuid().toString());



            isUpload = true;
        } catch (IOException e) {

            throw new RuntimeException("Failed to upload the image", e);
        } finally {

            try {
                imageProduct.getInputStream().close();
                imageProduct.transferTo(File.createTempFile("temp", ".tmp"));
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        return isUpload;
    }

}
