package com.westernhills.westernhills.service;

import com.westernhills.westernhills.entity.userEntity.User;
import com.westernhills.westernhills.entity.userEntity.UserAddress;
import com.westernhills.westernhills.repo.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;




    @Override
    public List<UserAddress> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public void deleteUserAddress(UUID id) {

    }

    @Override
    public Optional<UserAddress> getUserAddressById(UUID id) {
        return Optional.empty();
    }

    @Override
    public UserAddress save(UserAddress userAddress) {
        return addressRepository.save(userAddress);
    }

    @Override
    public List<UserAddress> findByUser(User user) {
        return addressRepository.findByUser(user);
    }


}
