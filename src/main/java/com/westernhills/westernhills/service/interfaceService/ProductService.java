package com.westernhills.westernhills.service.interfaceService;

import com.westernhills.westernhills.dto.ProductDto;
import com.westernhills.westernhills.entity.admin.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {



    List<Product> findAll();

    Optional<Product> findAllByID(UUID uuid);
    Product save( ProductDto productDto);


    void    deleteById(UUID id);

    void updateProduct(Product product);
    public List<Product> getAllProducts();
    public void addProduct(Product product);
    public void removeProductById(UUID id);
    public Optional<Product> getProductById(UUID id);
    public List<Product> getAllProductsByCategoryId(UUID id);

    List<Product> searchProductsByCategory(String category);


















}
