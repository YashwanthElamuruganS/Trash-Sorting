package com.tsmrf.tsmrf_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsmrf.tsmrf_management.model.VendorBill;

public interface VendorBillRepository
        extends JpaRepository<VendorBill, Long> {

    Optional<VendorBill> findByBillNumber(String billNumber);

    boolean existsByPurchaseOrderId(Long purchaseOrderId);
}