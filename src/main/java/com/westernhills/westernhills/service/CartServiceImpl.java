package com.westernhills.westernhills.service;

import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.entity.userEntity.Cart;
import com.westernhills.westernhills.entity.userEntity.User;
import com.westernhills.westernhills.repo.CartRepository;
import com.westernhills.westernhills.repo.ProductRepository;
import com.westernhills.westernhills.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class CartServiceImpl implements CartService{



    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;


    @Override
    public List<Cart> cartList(Cart cart) {
        return cartRepository.findAll();
    }



    @Override
    public void addToCartItem(String userName, UUID productId) {
        User user=userRepository.findByUsername(userName).orElse(null);


        Cart cart =new Cart();
        cart.setUser(user);
        cart.setProduct(productRepository.findById(productId).get());
        cart.setQuantity(1);
        cartRepository.save(cart);


    }




    @Override
    public Cart save(Cart cart) {
        return null;
    }



    @Override
    public void deleteAddedCart(Cart cart) {

    }




}
