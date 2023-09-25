package com.westernhills.westernhills.service.interfaceService;

import com.westernhills.westernhills.entity.admin.Image;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageService {


    List<Image> getAllFiles();
    void saveAllFilesList(List<Image> fileList);

    Image save (Image image);







}
