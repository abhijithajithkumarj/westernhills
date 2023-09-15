package com.westernhills.westernhills.service;

import com.westernhills.westernhills.dto.ProductDto;
import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.repo.ProductRepository;
import com.westernhills.westernhills.util.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageUpload imageUpload;


    @Override
    public List<Product>findAll() {
        return null;
    }

    @Override
    public Product save( ProductDto productDto) {
    Product product = new Product();
    product.setCategory(productDto.getCategory());
    return productRepository.save(product);
    }





    @Override
    public void deleteById(UUID productId) {
        Optional<Product> product=productRepository.findById(productId);
        product.ifPresent(product1 -> {
            product1.setDeleted(true);
            product1.setActivated(false);
            productRepository.save(product1);
        });

    }

    @Override
    public void updateProduct( Product product) {
        productRepository.save(product);

    }


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void addProduct(Product products) {
        productRepository.save(products);
    }

    @Override
    public void removeProductById(UUID id) {
        productRepository.deleteById(id);
    }

    public Optional<Product> getProductById(UUID id){
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAllProductsByCategoryId(UUID id){
        return productRepository.findAllByCategoryId(id).stream()
                .filter(product -> !product.isDeleted())
                .collect(Collectors.toList());
    }




}
