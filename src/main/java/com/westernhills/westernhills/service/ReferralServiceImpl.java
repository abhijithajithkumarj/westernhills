package com.westernhills.westernhills.service;

import com.westernhills.westernhills.entity.admin.CouponStatus;
import com.westernhills.westernhills.entity.userEntity.Referral;
import com.westernhills.westernhills.entity.userEntity.User;
import com.westernhills.westernhills.entity.wallet.Wallet;
import com.westernhills.westernhills.repo.ReferralRepository;
import com.westernhills.westernhills.repo.UserRepository;
import com.westernhills.westernhills.service.interfaceService.ReferralService;
import com.westernhills.westernhills.service.walleteservice.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ReferralServiceImpl implements ReferralService {

    @Autowired
    private ReferralRepository referralRepository;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private WalletService walletService;



    @Override
    public List<Referral> getAllReferrals() {
        return referralRepository.findAll();
    }

    @Override
    public Referral save(Referral reference) {
        return referralRepository.save(reference);
    }

    @Override
    public Optional<Referral> getFindId(UUID id) {
        return  referralRepository.findById(id);
    }

    @Override
    public void SaveReferralCode(String code, String username) {
        Optional<Referral> userName = referralRepository.findByUser_Username(username);
        Optional<User> user=userRepository.findByUsername(username);


        if (!userName.isPresent()){
            User user1=user.get();
            Referral referral=new Referral();

            referral.setReferralCode(code);
            referral.setUser(user1);
            referral.setCouponStatus(CouponStatus.Used);
            referralRepository.save(referral);

        }else {
            System.out.println("nothing");
        }




    }

    @Override
    public void setReferralsCodeDiscount(String code, String username) {
        Optional<Referral> referralCodeOptional = referralRepository.findByReferralCode(code);


        if (referralCodeOptional.isPresent()) {
            Referral referral = referralCodeOptional.get();
            int referralDiscount = 10;
            int referralAcceptedDiscount = 5;

            Optional<Referral> userOptional = referralRepository.findByUser_Username(username);


            if (!userOptional.isPresent()) {
                Optional<User> userOptionalByName = userRepository.findByUsername(username);


                if (userOptionalByName.isPresent()) {
                    User user = userOptionalByName.get();
                    String  referralUserName = String.valueOf(referral.getUser());


                    Referral useReferral = new Referral();
                    useReferral.setReferralCode(code);
                    useReferral.setUser(user);
                    useReferral.setCouponStatus(CouponStatus.referral_used);
                    referralRepository.save(useReferral);


                    List<Wallet> referralUserNameId=walletService.findByUser_Username(referralUserName);

                    User  referralUserNameUuid= referral.getUser();
                    Wallet referenceUserPrice=new Wallet();
                    referenceUserPrice.setReturnPayment((double) referralDiscount);
                    referenceUserPrice.setUser(referralUserNameUuid);
                    walletService.save(referenceUserPrice);






                    Optional<User> userUser=userRepository.findByUsername(username);
                    User referralAcceptUserId=userUser.get();
                    Wallet referenceAcceptPrice=new Wallet();
                    referenceAcceptPrice.setReturnPayment((double) referralAcceptedDiscount);
                    referenceAcceptPrice.setUser(referralAcceptUserId);
                    walletService.save(referenceAcceptPrice);
                }
            }
        }
    }


//    @Override
//    public void setReferralsCodeDiscount(String code, String username) {
//        Optional<Referral> referralCode= referralRepository.findByReferralCode(code);
//        Referral referral=referralCode.get();
//
//
//        int referralDiscount= 10;
//        int referralAcceptedDiscount= 5;
//
//
//        Optional<Referral> user=referralRepository.findByUser_Username(username);
//
//
//        if (referralCode.isPresent()){
//
//
//
//
//            Optional<Referral> referralCodeAcceptUser=referralRepository.findByUser_Username(username);
//
//
//
//            String referralUserName=referral.getUser().getUsername();
//            UUID providerId=referral.getId();
//
//
//
//            if (!user.isPresent()){
//
//                Optional<User> user1=userRepository.findByUsername(username);
//                User user2= user1.get();
//
//                Referral useReferral = new Referral();
//                useReferral.setReferralCode(code);
//                useReferral.setUser(user2);
//                useReferral.setCouponStatus(CouponStatus.referral_used);
//                referralRepository.save(useReferral);
//
//
//                List<Wallet> referralUserNameId=walletService.findByUser_Username(referralUserName);
//
//               User  referralUserNameUuid= referral.getUser();
//               Wallet referenceUserPrice=new Wallet();
//               referenceUserPrice.setReturnPayment((double) referralDiscount);
//               referenceUserPrice.setUser(referralUserNameUuid);
//               walletService.save(referenceUserPrice);
//
//
//
//
//
//
//               Optional<User> userUser=userRepository.findByUsername(username);
//               User referralAcceptUserId=userUser.get();
//                Wallet referenceAcceptPrice=new Wallet();
//                referenceAcceptPrice.setReturnPayment((double) referralAcceptedDiscount);
//                referenceAcceptPrice.setUser(referralAcceptUserId);
//                walletService.save(referenceAcceptPrice);
//
//
//
//
//
//
//
//
//
//
//
//
//            }
//
//
//
//
//        }
//
//
//    }


}
