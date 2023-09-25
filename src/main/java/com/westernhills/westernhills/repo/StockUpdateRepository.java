package com.westernhills.westernhills.repo;

import com.westernhills.westernhills.entity.admin.StockUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockUpdateRepository extends JpaRepository<StockUpdate , UUID> {


}
