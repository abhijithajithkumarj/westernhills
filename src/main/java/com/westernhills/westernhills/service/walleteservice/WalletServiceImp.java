package com.westernhills.westernhills.service.walleteservice;

import com.westernhills.westernhills.entity.wallet.Wallet;
import com.westernhills.westernhills.repo.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class WalletServiceImp implements WalletService {


    @Autowired
    private WalletRepository walletRepository;




    @Override
    public List<Wallet> findByAll() {
        return walletRepository.findAll();
    }

    @Override
    public List<Wallet> findByUser_Username(String username) {
        return walletRepository.findByUser_Username(username);
    }

    @Override
    public Optional<Wallet> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Wallet createWallet(Wallet wallet) {
        return null;
    }

    @Override
    public Wallet updateWallet(Wallet wallet) {
        return null;
    }

    @Override
    public void deleteWallet(UUID id) {

    }

    @Override
    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public double walletTotalPayment(String username) {
        List<Wallet> wallets=walletRepository.findByUser_Username(username);

        return wallets.stream()
                .mapToDouble(Wallet::getReturnPayment)
                .sum();
    }


}
