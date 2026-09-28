package com.tsmrf.tsmrf_management.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsmrf.tsmrf_management.model.Invoice;
import com.tsmrf.tsmrf_management.model.Payment;
import com.tsmrf.tsmrf_management.model.VendorBill;
import com.tsmrf.tsmrf_management.repository.InvoiceRepository;
import com.tsmrf.tsmrf_management.repository.PaymentRepository;
import com.tsmrf.tsmrf_management.repository.VendorBillRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final VendorBillRepository vendorBillRepository;
    private final InvoiceRepository invoiceRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            VendorBillRepository vendorBillRepository,
            InvoiceRepository invoiceRepository) {

        this.paymentRepository = paymentRepository;
        this.vendorBillRepository = vendorBillRepository;
        this.invoiceRepository = invoiceRepository;
    }

    // Create payment
    @Transactional
    public Payment create(Payment payment) {

        if (payment.getPaymentNumber() == null ||
                payment.getPaymentNumber().isBlank()) {
            throw new RuntimeException("Payment number is required");
        }

        if (payment.getPaymentType() == null ||
                payment.getPaymentType().isBlank()) {
            throw new RuntimeException("Payment type is required");
        }

        if (payment.getReferenceId() == null) {
            throw new RuntimeException("Reference ID is required");
        }

        if (payment.getAmount() == null ||
                payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Payment amount must be positive");
        }

        if (payment.getPaymentMethod() == null ||
                !"BANK".equalsIgnoreCase(payment.getPaymentMethod())) {
            throw new RuntimeException("Only BANK payments are supported");
        }

        payment.setPaymentMethod("BANK");

        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDate.now());
        }

        String type = payment.getPaymentType().trim().toUpperCase();

        if (type.equals("VENDOR_PAYMENT")) {

            VendorBill bill = vendorBillRepository
                    .findById(payment.getReferenceId())
                    .orElseThrow(() ->
                            new RuntimeException("Vendor bill not found"));

            if ("PAID".equalsIgnoreCase(bill.getStatus())) {
                throw new RuntimeException("Bill is already paid");
            }

           BigDecimal paymentAmount = payment.getAmount();
BigDecimal billAmount = bill.getTotalAmount();

if (paymentAmount == null || billAmount == null) {
    throw new RuntimeException("Payment or bill amount is missing");
}

if (paymentAmount.compareTo(billAmount) != 0) {
    throw new RuntimeException(
        "Payment amount " + paymentAmount
        + " does not match bill amount " + billAmount
    );
}
            bill.setStatus("PAID");
            vendorBillRepository.save(bill);

        } else if (type.equals("CUSTOMER_RECEIPT")) {

            Invoice invoice = invoiceRepository
                    .findById(payment.getReferenceId())
                    .orElseThrow(() ->
                            new RuntimeException("Customer invoice not found"));

            if ("PAID".equalsIgnoreCase(invoice.getStatus())) {
                throw new RuntimeException("Invoice is already paid");
            }

            BigDecimal paymentAmount = payment.getAmount();
            BigDecimal invoiceAmount = invoice.getTotalAmount();

            if (invoiceAmount == null ||
                    paymentAmount.compareTo(invoiceAmount) != 0) {
                throw new RuntimeException(
                        "Payment amount does not match invoice amount");
            }

            invoice.setStatus("PAID");
            invoiceRepository.save(invoice);

        } else {
            throw new RuntimeException(
                    "Invalid payment type. Use VENDOR_PAYMENT or CUSTOMER_RECEIPT");
        }

        payment.setPaymentType(type);
        return paymentRepository.save(payment);
    }

    // Get all payments
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    // Get payment by ID
    public Payment getById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found: " + id));
    }
}