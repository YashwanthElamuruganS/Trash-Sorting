package com.tsmrf.tsmrf_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsmrf.tsmrf_management.model.SalesOrder;

public interface SalesOrderRepository
        extends JpaRepository<SalesOrder, Long> {
}