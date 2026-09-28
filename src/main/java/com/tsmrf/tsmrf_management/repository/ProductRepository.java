package com.tsmrf.tsmrf_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsmrf.tsmrf_management.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}