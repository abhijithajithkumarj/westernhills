package com.westernhills.westernhills.repo;

import com.westernhills.westernhills.entity.userEntity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

}
