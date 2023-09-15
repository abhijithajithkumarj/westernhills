package com.westernhills.westernhills.service;


import com.westernhills.westernhills.entity.admin.Category;
import com.westernhills.westernhills.entity.admin.Offer;
import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.repo.CategoryRepository;
import com.westernhills.westernhills.repo.OfferRepository;
import com.westernhills.westernhills.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OfferServiceImpl implements  OfferService{

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
                Offer offer1 = new Offer();
                offer1.setOfferName(offer.getOfferName());
                offer1.setOfferPrice(offer.getOfferPrice());
                offer1.setCategory(offer.getCategory());
                offer1.setExpirationDate(offer.getExpirationDate());
                offerRepository.save(offer1);
                System.out.println("problem");
                return null;

    }

    @Override
    public Optional<Offer> findbyCategory(String category) {
        return offerRepository.findByCategoryName(category);
    }


}
