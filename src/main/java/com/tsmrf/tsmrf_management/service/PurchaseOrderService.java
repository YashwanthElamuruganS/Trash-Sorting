package com.tsmrf.tsmrf_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tsmrf.tsmrf_management.model.PurchaseOrder;
import com.tsmrf.tsmrf_management.repository.PurchaseOrderRepository;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository repository;

    public PurchaseOrderService(PurchaseOrderRepository repository) {
        this.repository = repository;
    }

    public PurchaseOrder create(PurchaseOrder order) {
        if (order.getOrderNumber() == null ||
                order.getOrderNumber().isBlank() ||
                order.getVendorName() == null ||
                order.getVendorName().isBlank() ||
                order.getItemDescription() == null ||
                order.getItemDescription().isBlank() ||
                order.getQuantity() == null ||
                order.getQuantity() <= 0 ||
                order.getUnitPrice() == null ||
                order.getUnitPrice().signum() <= 0 ||
                order.getOrderDate() == null) {
            throw new IllegalArgumentException(
                    "Provide valid order details, quantity, price and date");
        }

        if (repository.findByOrderNumber(order.getOrderNumber())
                .isPresent()) {
            throw new IllegalArgumentException(
                    "Purchase order number already exists");
        }

        if (order.getExpectedDate() != null &&
                order.getExpectedDate().isBefore(order.getOrderDate())) {
            throw new IllegalArgumentException(
                    "Expected date cannot be before order date");
        }

        order.setStatus("PENDING");
        return repository.save(order);
    }

    public List<PurchaseOrder> getAll() {
        return repository.findAll();
    }

    public PurchaseOrder getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Purchase order not found: " + id));
    }

    public PurchaseOrder updateStatus(Long id, String status) {
        PurchaseOrder order = getById(id);

        if (!List.of("PENDING", "APPROVED", "RECEIVED", "CANCELLED")
                .contains(status.toUpperCase())) {
            throw new IllegalArgumentException("Invalid status");
        }

        order.setStatus(status.toUpperCase());
        return repository.save(order);
    }
}