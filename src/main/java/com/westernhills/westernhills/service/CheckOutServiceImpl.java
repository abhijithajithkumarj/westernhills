package com.westernhills.westernhills.service;

import com.westernhills.westernhills.entity.admin.CouponStatus;
import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.entity.admin.UseCoupon;
import com.westernhills.westernhills.entity.userEntity.*;
import com.westernhills.westernhills.entity.wallet.Wallet;
import com.westernhills.westernhills.repo.*;
import com.westernhills.westernhills.service.interfaceService.CartService;
import com.westernhills.westernhills.service.interfaceService.CheckOutService;
import com.westernhills.westernhills.service.interfaceService.ProductService;
import com.westernhills.westernhills.service.walleteservice.WalletService;
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
    private ProductService productService;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private AddressRepository addressRepository;


    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private UseCouponRepository useCouponRepository;



    @Autowired
    private CartService cartService;


    @Autowired
    private WalletService walletService;


//    @Autowired
//    private CheckOutService checkOutService;





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


            Product product = cartItem.getProduct();
            UUID productId = product.getUuid();


            Optional<Product> productOptional = productService.findAllByID(productId);

            if (productOptional.isPresent()) {

                Product retrievedProduct = productOptional.get();

                double stock=retrievedProduct.getStock();
                double checkoutQuantity=cartItem.getQuantity();
                double finalStock=stock-checkoutQuantity;
                product.setStock(finalStock);
                productRepository.save(product);




            }



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



            Product product = cartItem.getProduct();
            UUID productId = product.getUuid();


            Optional<Product> productOptional = productService.findAllByID(productId);

            if (productOptional.isPresent()) {

                Product retrievedProduct = productOptional.get();

                double stock=retrievedProduct.getStock();
                double checkoutQuantity=cartItem.getQuantity();
                double finalStock=stock-checkoutQuantity;

                System.out.println(stock);
                System.out.println(checkoutQuantity);
                System.out.println(finalStock);



                product.setStock(finalStock);
                productRepository.save(product);




            }
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
    public Optional<CheckOut> orderReturn(UUID id) {
        Optional<CheckOut> orderReturn = checkOutRepository.findById(id);
        if (orderReturn.isPresent()) {
            CheckOut userOrderReturn=orderReturn.get();
            userOrderReturn.setOrderStatus(OrderStatus.ORDER_RETURNED);
            checkOutRepository.save(userOrderReturn);

        }


        return orderReturn;
    }

    @Override
    public Optional<CheckOut> getProductId(UUID id) {
        return  checkOutRepository.findById(id);

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







    @Override
    public void orderCancellationAndMoneyTransferringInWallet(OrderStatus orderStatus, UUID id, String username) {
        Optional<CheckOut> userOrderStatusSetting=checkOutRepository.findById(id);

        User checkOutUser=userOrderStatusSetting.get().getUser();
        Product product1= userOrderStatusSetting.get().getProduct();

        Wallet wallet=new Wallet();


        if (userOrderStatusSetting.isPresent()) {


            Product product= userOrderStatusSetting.get().getProduct();
            UUID productId=product.getUuid();


            Optional<UseCoupon> usedCoupon=useCouponRepository.findByProduct_Uuid(productId);

            List<UseCoupon> user=useCouponRepository.findByUser_Username(username);


            List<Long> userid = user.stream()
                    .map(useCoupon -> useCoupon.getUser().getId())
                    .collect(Collectors.toList());



            if (usedCoupon.isPresent()) {


                User user1= (User) user;
                double discountPrice=usedCoupon.stream()
                        .filter(useCoupon ->
                                useCoupon.getUser().equals(userid) &&
                                        useCoupon.isAppliedProductCoupon() &&
                                        useCoupon.getCouponStatus() == CouponStatus.Used &&
                                        useCoupon.getProduct().getUuid().equals(productId))
                        .mapToDouble(useCoupon -> useCoupon.getDiscountPrice())
                        .sum();

                CheckOut userOrderProduct=userOrderStatusSetting.get();
                wallet.setReturnPayment((double) (product.getSelPrice()-discountPrice));
                wallet.setUser(user1);
                userOrderProduct.setOrderStatus(orderStatus);
                checkOutRepository.save(userOrderProduct);
                walletService.save(wallet);


            }else {
                CheckOut userOrderProduct=userOrderStatusSetting.get();
                wallet.setReturnPayment(product.getSelPrice());
                wallet.setUser(checkOutUser);
                userOrderProduct.setOrderStatus(orderStatus);
                checkOutRepository.save(userOrderProduct);
                walletService.save(wallet);
            }



        }else {
            CheckOut userOrderProduct=userOrderStatusSetting.get();
            double amount = product1.getSelPrice();
            CheckOut userOrderStatus = userOrderStatusSetting.get();
            userOrderStatus.setOrderStatus(orderStatus);
            wallet.setReturnPayment(amount);
            wallet.setUser(checkOutUser);
            userOrderProduct.setOrderStatus(orderStatus);
            walletService.save(wallet);
            checkOutRepository.save(userOrderProduct);

        }

    }


}
