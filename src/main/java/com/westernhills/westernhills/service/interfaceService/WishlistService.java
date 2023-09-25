package com.westernhills.westernhills.service.interfaceService;

import com.westernhills.westernhills.entity.userEntity.CheckOut;
import com.westernhills.westernhills.entity.userEntity.OrderStatus;
import com.westernhills.westernhills.entity.userEntity.Wishlist;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WishlistService {




    List<Wishlist> wishlistProduct(String username);

    void addToWishlist(String userName, UUID productId);

    List<Wishlist> wishlistFindByUserName(String userName);


    List<Wishlist> findAll();

    void deleteWishlist(UUID id);









}
