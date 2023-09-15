package com.westernhills.westernhills.repo;

import com.westernhills.westernhills.entity.admin.CategoryUseCoupon;
import com.westernhills.westernhills.entity.admin.UseCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryUseCouponRepository extends JpaRepository<CategoryUseCoupon, UUID> {


    List<CategoryUseCoupon> findByUser_Username(String username);



}
