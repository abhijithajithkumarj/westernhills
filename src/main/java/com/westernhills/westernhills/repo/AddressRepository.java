package com.westernhills.westernhills.repo;

import com.westernhills.westernhills.entity.userEntity.Cart;
import com.westernhills.westernhills.entity.userEntity.User;
import com.westernhills.westernhills.entity.userEntity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface AddressRepository extends JpaRepository<UserAddress, UUID> {

    List<UserAddress> findByUser(User users);

    List<UserAddress> findByUser_Username(String username);


}

