package com.westernhills.westernhills.service;

import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.entity.userEntity.User;
import com.westernhills.westernhills.entity.userEntity.Wishlist;
import com.westernhills.westernhills.repo.UserRepository;
import com.westernhills.westernhills.repo.WishlistRepository;
import com.westernhills.westernhills.service.interfaceService.ProductService;
import com.westernhills.westernhills.service.interfaceService.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private ProductService productService;


    @Autowired
    private UserService userService;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private WishlistRepository wishlistRepository;


    @Override
    public List<Wishlist> wishlistProduct(String username) {
        return null;
    }

    @Override
    public void addToWishlist(String userName, UUID productId) {
          Optional<Product> product= productService.findAllByID(productId);
          Optional<User> user=userRepository.findByUsername(userName);
          User userId= user.get();

          Product product1= product.get();
          Wishlist wishlist=new Wishlist();
          wishlist.setUser(userId);
          wishlist.setProduct(product1);
          wishlistRepository.save(wishlist);

    }

    @Override
    public List<Wishlist> wishlistFindByUserName(String userName) {
        return wishlistRepository.findByUser_Username(userName);
    }

    @Override
    public List<Wishlist> findAll() {
        return null;
    }

    @Override
    public void deleteWishlist(UUID id) {
        wishlistRepository.deleteById(id);
    }


}
