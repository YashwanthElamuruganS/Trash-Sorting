package com.tsmrf.tsmrf_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsmrf.tsmrf_management.model.Invoice;

public interface InvoiceRepository
        extends JpaRepository<Invoice, Long> {

    boolean existsByInvoiceNumber(String invoiceNumber);
}