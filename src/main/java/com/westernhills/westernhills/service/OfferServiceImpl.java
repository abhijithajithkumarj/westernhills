package com.westernhills.westernhills.service;


import com.westernhills.westernhills.entity.admin.Offer;
import com.westernhills.westernhills.repo.CategoryRepository;
import com.westernhills.westernhills.repo.OfferRepository;
import com.westernhills.westernhills.repo.ProductRepository;
import com.westernhills.westernhills.service.interfaceService.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepository offerRepository;



    @Autowired
    private ProductRepository productRepository;



    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Offer offerPrice(Offer offer) {
        return null;
    }


    @Override
    public Offer offerSetting(Offer offer) {
        offerSet(offer);

        return null;

    }

    private void offerSet(Offer offer) {
        Offer offer1 = new Offer();
        offer1.setOfferName(offer.getOfferName());
        offer1.setOfferPrice(offer.getOfferPrice());
        offer1.setCategory(offer.getCategory());
        offer1.setExpirationDate(offer.getExpirationDate());
        offerRepository.save(offer1);
    }

    @Override
    public Optional<Offer> findbyCategory(String category) {
        return offerRepository.findByCategoryName(category);
    }


}
