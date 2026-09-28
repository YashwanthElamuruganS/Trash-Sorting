package com.tsmrf.tsmrf_management.service;

import com.tsmrf.tsmrf_management.model.Invoice;
import com.tsmrf.tsmrf_management.repository.InvoiceRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    // Create invoice
    public Invoice create(Invoice invoice) {

        if (invoice.getInvoiceNumber() == null ||
                invoice.getInvoiceNumber().isBlank()) {
            throw new RuntimeException("Invoice number is required");
        }

        if (invoiceRepository.existsByInvoiceNumber(
                invoice.getInvoiceNumber())) {
            throw new RuntimeException("Invoice number already exists");
        }

        if (invoice.getBrokerName() == null ||
                invoice.getBrokerName().isBlank()) {
            throw new RuntimeException("Broker name is required");
        }

        if (invoice.getMaterialName() == null ||
                invoice.getMaterialName().isBlank()) {
            throw new RuntimeException("Material name is required");
        }

        if (invoice.getQuantity() == null ||
                invoice.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        if (invoice.getUnitPrice() == null ||
                invoice.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Unit price must be greater than zero");
        }

        if (invoice.getInvoiceDate() == null) {
            invoice.setInvoiceDate(LocalDate.now());
        }

        if (invoice.getDueDate() != null &&
                invoice.getDueDate().isBefore(invoice.getInvoiceDate())) {
            throw new RuntimeException(
                    "Due date cannot be before invoice date");
        }

        invoice.setStatus("PENDING");
        invoice.calculateTotal();

        return invoiceRepository.save(invoice);
    }

    // Get all invoices
    @Transactional(readOnly = true)
    public List<Invoice> getAll() {
        return invoiceRepository.findAll();
    }

    // Get invoice by ID
    @Transactional(readOnly = true)
    public Invoice getById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Invoice not found: " + id));
    }

    // Update invoice
    public Invoice update(Long id, Invoice updatedInvoice) {

        Invoice invoice = getById(id);

        if (updatedInvoice.getBrokerName() == null ||
                updatedInvoice.getBrokerName().isBlank()) {
            throw new RuntimeException("Broker name is required");
        }

        if (updatedInvoice.getMaterialName() == null ||
                updatedInvoice.getMaterialName().isBlank()) {
            throw new RuntimeException("Material name is required");
        }

        if (updatedInvoice.getQuantity() == null ||
                updatedInvoice.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        if (updatedInvoice.getUnitPrice() == null ||
                updatedInvoice.getUnitPrice()
                        .compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Unit price must be greater than zero");
        }

        invoice.setBrokerName(updatedInvoice.getBrokerName());
        invoice.setBrokerEmail(updatedInvoice.getBrokerEmail());
        invoice.setMaterialName(updatedInvoice.getMaterialName());
        invoice.setQuantity(updatedInvoice.getQuantity());
        invoice.setUnitPrice(updatedInvoice.getUnitPrice());
        invoice.setInvoiceDate(updatedInvoice.getInvoiceDate());
        invoice.setDueDate(updatedInvoice.getDueDate());

        invoice.calculateTotal();

        return invoiceRepository.save(invoice);
    }

    // Update invoice payment status
    public Invoice updateStatus(Long id, String status) {

        Invoice invoice = getById(id);

        if (!List.of("PENDING", "PAID", "CANCELLED")
                .contains(status.toUpperCase())) {
            throw new RuntimeException("Invalid invoice status");
        }

        invoice.setStatus(status.toUpperCase());

        return invoiceRepository.save(invoice);
    }

    // Delete invoice
    public void delete(Long id) {
        Invoice invoice = getById(id);
        invoiceRepository.delete(invoice);
    }
}