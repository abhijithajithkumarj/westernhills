package com.westernhills.westernhills.service;

import com.westernhills.westernhills.entity.userEntity.User;
import com.westernhills.westernhills.entity.userEntity.UserAddress;
import com.westernhills.westernhills.repo.AddressRepository;
import com.westernhills.westernhills.service.interfaceService.AddressService;
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
    public void disableAddress(UUID uuid) {
          Optional<UserAddress> userAddress= addressRepository.findById(uuid);
          UserAddress userAddressi=userAddress.get();
          userAddressi.setDeleted(true);
          addressRepository.save(userAddressi);
    }



    @Override
    public List<UserAddress> findByUser(User user) {
        return addressRepository.findByUser(user);
    }








    @Override
    public void updateAddress(UUID id, UserAddress address) {

        Optional<UserAddress> userAddress=addressRepository.findById(id);
        System.out.println(userAddress);
        UserAddress updatedAddress= userAddress.get();


        updatedAddress.setHouseName(address.getHouseName());
        updatedAddress.setCity(address.getCity());
        updatedAddress.setArea(address.getArea());
        updatedAddress.setLandMarker(address.getLandMarker());
        updatedAddress.setState(address.getState());
        updatedAddress.setTown(address.getTown());
        updatedAddress.setPin(address.getPin());
        updatedAddress.setDefaultAddress(address.isDefaultAddress());
        updatedAddress.setEnabled(address.isEnabled());
        addressRepository.save(updatedAddress);



    }






}
