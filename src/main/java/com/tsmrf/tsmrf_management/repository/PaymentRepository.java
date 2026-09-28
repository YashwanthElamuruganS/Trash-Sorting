package com.tsmrf.tsmrf_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsmrf.tsmrf_management.model.Payment;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentNumber(String paymentNumber);
}