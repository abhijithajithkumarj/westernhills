package com.westernhills.westernhills.repo;


import com.westernhills.westernhills.entity.userEntity.Cart;
import com.westernhills.westernhills.entity.userEntity.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface CheckOutRepository extends JpaRepository<CheckOut, UUID> {

    List<CheckOut> findByUser_Username(String username);

    List<CheckOut> findByCreatedAtBetween(Date startDate, Date endDate);







}
