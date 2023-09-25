package com.westernhills.westernhills.repo;

import com.westernhills.westernhills.entity.admin.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {


    @Query(value = "SELECT c FROM Category c WHERE c.activated = true AND c.deleted = true")
    List<Category> findAllByActivated();


    Optional<Category> findByName(String name);










}
