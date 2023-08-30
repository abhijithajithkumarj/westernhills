package com.westernhills.westernhills.service;

import com.westernhills.westernhills.entity.userEntity.Cart;
import com.westernhills.westernhills.entity.userEntity.User;
import com.westernhills.westernhills.repo.CartRepository;
import com.westernhills.westernhills.repo.ProductRepository;
import com.westernhills.westernhills.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private CartRepository  cartRepository;


    @Override
    public List<Cart> cartList(Cart cart) {
        return cartRepository.findAll();
    }






    @Override
    public void addToCartItem(String userName, UUID productId) {
        User user = userRepository.findByUsername(userName).orElse(null);


        Optional<Cart> existingCart = cartRepository.findByUserAndProduct(user, productRepository.findById(productId).orElse(null));

        if (existingCart.isPresent()) {

            Cart cart = existingCart.get();
            cart.setQuantity(cart.getQuantity() + 1);
            cartRepository.save(cart);
        } else {

            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(productRepository.findById(productId).orElse(null));
            cart.setQuantity(1);
            cartRepository.save(cart);
        }
    }




    @Override
    public void addQuantity(String username, UUID cartId, int quantity) {



        User user=userRepository.findByUsername(username).orElse(null);
        Cart cart1=cartRepository.findById(cartId).get();
        cart1.setQuantity(quantity);
        cart1.setUser(user);
        cartRepository.save(cart1);

    }




    @Override
    public Cart checkOut(String userName) {
        User user =userRepository.findByUsername(userName).orElse(null);

        if (user != null) {
            List<Cart> carts = cartRepository.findAll();

            Cart cartUser = null;
            for (Cart cart : carts) {
                if (cart.getUser().getId().equals(user.getId())) {
                    cartUser = cart;
                    System.out.println(cartUser);
                }
            }


           return cartUser;
        }


        return null;
    }

    @Override
    public Cart findByUserId(String userName) {


        return null;
    }

    @Override
    public double getTotalPrice(String username) {

        List<Cart> cartItems = cartRepository.findByUser_Username(username);

        return cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getSelPrice() * cartItem.getQuantity())
                .sum();

    }





    @Override
    public List<Cart> getCartItems(String username) {
        return cartRepository.findByUser_Username(username);


    }

    @Override
    public void removeCart(String username) {


    }


    @Override
    public Cart save(Cart cart) {
        return null;
    }



    @Override
    public void deleteAddedCart(Cart cart) {

    }






}
