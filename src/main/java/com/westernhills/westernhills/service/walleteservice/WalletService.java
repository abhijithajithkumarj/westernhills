package com.westernhills.westernhills.service.walleteservice;

import com.westernhills.westernhills.entity.wallet.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletService {

    List<Wallet> findByAll();


    List<Wallet> findByUser_Username(String username);



    Optional<Wallet> findById(UUID id);

    Wallet createWallet(Wallet wallet);


    Wallet updateWallet(Wallet wallet);


    void deleteWallet(UUID id);

    Wallet save(Wallet wallet);

    double walletTotalPayment(String username);




}
