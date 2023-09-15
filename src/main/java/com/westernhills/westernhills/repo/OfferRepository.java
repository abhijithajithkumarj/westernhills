package com.westernhills.westernhills.repo;

import com.westernhills.westernhills.entity.admin.Category;
import com.westernhills.westernhills.entity.admin.Offer;
import com.westernhills.westernhills.entity.admin.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {


    Optional<Category> findByCategory(UUID category);;

    Optional<Offer> findByCategoryName(String category);


}
