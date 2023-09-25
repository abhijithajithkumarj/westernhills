package com.westernhills.westernhills.repo;

import com.westernhills.westernhills.entity.userEntity.Wishlist;
import com.westernhills.westernhills.entity.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    List<Wallet> findByUser_Username(String username);





}
