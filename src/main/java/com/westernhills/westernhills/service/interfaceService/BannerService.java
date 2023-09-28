package com.westernhills.westernhills.service.interfaceService;

import com.westernhills.westernhills.entity.admin.Banner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BannerService {


     Banner addBanner(Banner banner, List<MultipartFile> files) throws IOException;

    List<Banner>  findAll();





}
