package com.westernhills.westernhills.service;

import com.westernhills.westernhills.dto.ProductDto;
import com.westernhills.westernhills.entity.admin.Category;
import com.westernhills.westernhills.entity.admin.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {



    List<Product> findAll();
    Product save( ProductDto productDto);


    void    deleteById(UUID id);

    void updateProduct(Product product);
    public List<Product> getAllProducts();
    public void addProduct(Product product);
    public void removeProductById(UUID id);
    public Optional<Product> getProductById(UUID id);
    public List<Product> getAllProductsByCategoryId(UUID id);














}
