package com.westernhills.westernhills.repo;

import com.westernhills.westernhills.entity.userEntity.Referral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReferralRepository extends JpaRepository<Referral, UUID> {


    Optional<Referral> findByUser_Username(String username);

    Optional<Referral> findByReferralCode(String code);




}
