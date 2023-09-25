package com.westernhills.westernhills.service.interfaceService;

import com.westernhills.westernhills.entity.userEntity.Referral;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReferralService {


    List<Referral> getAllReferrals();


    Referral save(Referral reference);



    Optional<Referral> getFindId(UUID id);

    void  SaveReferralCode(String code,String username);

    void  setReferralsCodeDiscount(String code,String username);


}
