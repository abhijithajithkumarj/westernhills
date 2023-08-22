package com.westernhills.westernhills.service;

import com.westernhills.westernhills.entity.userEntity.Cart;

import java.util.List;
import java.util.UUID;

public interface CartService {

    List<Cart> cartList( Cart cart);

    void addToCartItem(String userName, UUID productId);


    Cart save (Cart cart);


     void deleteAddedCart(Cart cart);
     void  addQuantity(String username, UUID cartId, int quantity);

    Cart checkOut(String userName);

    Cart findByUserId(String userName);


    double getTotalPrice(String username);


    List<Cart> getCartItems(String username);





}
