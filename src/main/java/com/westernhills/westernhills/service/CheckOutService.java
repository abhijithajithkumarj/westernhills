package com.westernhills.westernhills.service;

import com.westernhills.westernhills.entity.userEntity.Cart;
import com.westernhills.westernhills.entity.userEntity.CheckOut;

import java.util.List;
import java.util.UUID;

public interface CheckOutService {

    double getTotalPrice(String username);

    List<CheckOut> getCartItems(String username,UUID id);

    void addToCartItem(String userName, UUID productId);
}
