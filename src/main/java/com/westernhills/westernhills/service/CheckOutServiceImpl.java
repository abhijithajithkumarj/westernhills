package com.westernhills.westernhills.service;

import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.entity.userEntity.Cart;
import com.westernhills.westernhills.entity.userEntity.CheckOut;
import com.westernhills.westernhills.entity.userEntity.User;
import com.westernhills.westernhills.entity.userEntity.UserAddress;
import com.westernhills.westernhills.repo.AddressRepository;
import com.westernhills.westernhills.repo.CartRepository;
import com.westernhills.westernhills.repo.CheckOutRepository;
import com.westernhills.westernhills.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class CheckOutServiceImpl implements CheckOutService {

    @Autowired
    private CheckOutRepository checkOutRepository;


    @Autowired
    private CartRepository cartRepository;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private AddressRepository addressRepository;



    @Override
    public double getTotalPrice(String username) {
        return 0;
    }



//    @Override
//    public List<CheckOut> getCartItems(String username, UUID id) {
//        List<Cart> cartItems = cartRepository.findByUser_Username(username);
//        Optional<UserAddress> userAddress = addressRepository.findById(id);
//
//        return cartItems.stream()
//                .map(cartItem -> {
//                    Product product = cartItem.getProduct();
//
//                    // Check if there is already a CheckOut item for this product
//                    boolean existingCheckOut = cartItems.stream()
//                            .anyMatch(checkOut -> checkOut.getProduct().equals(product));
//
//                    if (!existingCheckOut) {
//                        CheckOut checkOutItem = new CheckOut();
//                        checkOutItem.setUser(cartItem.getUser());
//                        checkOutItem.setProduct(product);
//                        checkOutItem.setCod(true);
//                        checkOutItem.setUserAddress(userAddress.orElse(null));
//
//                        // Save the CheckOut item
//                        checkOutRepository.save(checkOutItem);
//
//                        // Fetch the Cart ID before deleting
//                        UUID cartId = cartItem.getId();
//
//                        // Delete the cart item
//                        cartRepository.delete(cartItem);
//
//                        System.out.println("Cart ID: " + cartId);
//
//                        return checkOutItem;
//                    }
//                    return null; // Return null for existing items
//                })
//                .filter(Objects::nonNull) // Filter out the null results (existing items)
//                .collect(Collectors.toList());
//    }


    @Override
    public List<CheckOut> getCartItems(String username, UUID id) {
        List<Cart> cartItems = cartRepository.findByUser_Username(username);
        Optional<UserAddress> userAddress = addressRepository.findById(id);

        List<CheckOut> checkOutItems = new ArrayList<>();

        for (Cart cartItem : cartItems) {
            Product product = cartItem.getProduct();
            Cart cart = cartItem;
            System.out.println(cart);

            Optional<CheckOut> existingCheckOut = checkOutItems.stream()
                    .filter(checkOut -> checkOut.getProduct().equals(product))
                    .findFirst();

            if (existingCheckOut.isPresent()) {
                System.out.println("Not existing check");
            } else {
                CheckOut checkOutItem = new CheckOut();
                checkOutItem.setUser(cartItem.getUser());
                checkOutItem.setProduct(product);
                checkOutItem.setCod(true);
                checkOutItem.setUserAddress(userAddress.orElse(null));


                checkOutRepository.save(checkOutItem);


                UUID cartId = cart.getId();

                cartRepository.delete(cart);

                System.out.println("Cart ID: " + cartId);
            }
        }

        return checkOutItems;
    }







    @Override
    public void addToCartItem(String userName, UUID productId) {

    }
}
