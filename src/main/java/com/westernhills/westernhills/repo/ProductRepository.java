package com.westernhills.westernhills.repo;

import com.westernhills.westernhills.entity.admin.Category;
import com.westernhills.westernhills.entity.admin.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {



    List<Product> findAllByCategoryId(UUID id);





}
