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
        System.out.println(user);

        Cart cart =new Cart();
        cart.setUser(user);
        cart.setProduct(productRepository.findById(productId).get());
        cart.setQuantity(1);
        cartRepository.save(cart);


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
    public double totalPrice() {
        return cartRepository.findAll().stream()
                .filter(cartItem -> cartItem != null && cartItem.getProduct() != null)
                .mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getProduct()
                        .getSelPrice()).sum();
    }


    @Override
    public Cart save(Cart cart) {
        return null;
    }



    @Override
    public void deleteAddedCart(Cart cart) {

    }


//         <select style="width: 100px;" class="form-select me-2" name="quantity">
//                                                <option th:each="quantity : ${#numbers.sequence(1, 10)}"
//    th:value="${quantity}" th:text="${quantity}"
//    th:selected="${quantity == item.quantity}">1</option>
//                                            </select>





}
