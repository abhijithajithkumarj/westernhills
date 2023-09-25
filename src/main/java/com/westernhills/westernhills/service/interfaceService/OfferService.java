package com.westernhills.westernhills.service.interfaceService;

import com.westernhills.westernhills.entity.admin.Offer;

import java.util.Optional;

public interface OfferService {


    Offer offerPrice(Offer offer);

    Offer offerSetting(Offer offer);

    Optional<Offer> findbyCategory(String category);







}
