package com.westernhills.westernhills.service;

import com.westernhills.westernhills.entity.admin.Image;
import com.westernhills.westernhills.repo.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ImageServiceImpl implements ImageService{


    @Autowired
    private ImageRepository imageRepository;



    @Override
    public List<Image> getAllFiles() {
        return imageRepository.findAll();
    }

    @Override
    public void saveAllFilesList(List<Image> fileList) {
        for (Image fileModal : fileList)
            imageRepository.save(fileModal);

    }

    @Override
    public Image save(Image image) {


        return imageRepository.save(image);
    }


}
