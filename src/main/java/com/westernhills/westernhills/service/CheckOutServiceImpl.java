package com.westernhills.westernhills.service;

import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.entity.admin.UseCoupon;
import com.westernhills.westernhills.entity.userEntity.*;
import com.westernhills.westernhills.repo.*;
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


    @Autowired
    private UseCouponRepository useCouponRepository;



    @Autowired
    private CartService cartService;



    @Override
    public double getTotalPrice(String username) {
        return 0;
    }






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
                checkOutItem.setPaymentMethod(PaymentMethod.COD);
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
    public List<CheckOut> getCartItemsAll(String username, UUID id) {

        List<Cart> cartItems = cartRepository.findByUser_Username(username);
        System.out.println(cartItems);

        Optional<UserAddress> userAddress = addressRepository.findById(id);
        System.out.println(userAddress);
        double cartTotal=cartService.getTotalPrice(username);
        List<CheckOut> checkOutItems = new ArrayList<>();


        for (Cart cartItem : cartItems) {

            int count=cartItem.getQuantity();
            CheckOut checkOutItem = new CheckOut();
            checkOutItem.setUserAddress(userAddress.orElse(null));
            checkOutItem.setUser(userRepository.findByUsername(username).orElse(null));
            checkOutItem.setPaymentMethod(PaymentMethod.COD);
            checkOutItem.setProduct(cartItem.getProduct());
            checkOutItem.setCount(cartItem.getQuantity());
            checkOutItem.setOrderStatus(OrderStatus.ORDER_PENDING);
            checkOutRepository.save(checkOutItem);




            UUID cartId = cartItem.getId();
            System.out.println(cartId);
            cartRepository.delete(cartItem);
            checkOutItems.add(checkOutItem);
        }

        return checkOutItems;

    }







    @Override
    public List<CheckOut> getOnlineCheckout(String username, UUID id) {

        List<Cart> cartItems = cartRepository.findByUser_Username(username);
        System.out.println(cartItems);



        Optional<UserAddress> userAddress = addressRepository.findById(id);
        System.out.println(userAddress);

        double cartTotal=cartService.getTotalPrice(username);
        List<CheckOut> OnlineCheckOut = new ArrayList<>();


        for (Cart cartItem : cartItems) {
            int count=cartItem.getQuantity();
            CheckOut checkOutItem = new CheckOut();
            checkOutItem.setUserAddress(userAddress.orElse(null));
            checkOutItem.setUser(userRepository.findByUsername(username).orElse(null));
            checkOutItem.setPaymentMethod(PaymentMethod.ONLINE);
            checkOutItem.setProduct(cartItem.getProduct());
            checkOutItem.setCount(cartItem.getQuantity());
            checkOutItem.setOrderStatus(OrderStatus.ORDER_PENDING);
            checkOutRepository.save(checkOutItem);




            UUID cartId = cartItem.getId();


            System.out.println(cartId);
            cartRepository.delete(cartItem);
            OnlineCheckOut.add(checkOutItem);
        }

        return OnlineCheckOut;

    }



    @Override
    public List<CheckOut> getAllOrder(String username) {
        return checkOutRepository.findByUser_Username(username);
    }


    @Override
    public void addToCartItem(String userName, UUID productId) {

    }

    @Override
    public List<CheckOut> findAll() {
        return checkOutRepository.findAll();
    }

    @Override
    public Optional<CheckOut> canselProduct(UUID id) {
         Optional<CheckOut> optionalCheckOut = checkOutRepository.findById(id);
        if (optionalCheckOut.isPresent()) {
            CheckOut checkOut = optionalCheckOut.get();
            checkOut.setOrderStatus(OrderStatus.ORDER_CANCEL_PENDING);
            checkOutRepository.save(checkOut);
        }
         return optionalCheckOut;
    }

    @Override
    public void orderStatusSetting(OrderStatus orderStatus, UUID id) {
        Optional<CheckOut> userOrderStatusSetting=checkOutRepository.findById(id);
        if (userOrderStatusSetting.isPresent()) {
            CheckOut userOrderStatus = userOrderStatusSetting.get();
            userOrderStatus.setOrderStatus(orderStatus);
            checkOutRepository.save(userOrderStatus);
        }
    }


}
