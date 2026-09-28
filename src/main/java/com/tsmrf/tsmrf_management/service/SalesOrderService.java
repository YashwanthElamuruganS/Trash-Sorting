package com.tsmrf.tsmrf_management.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tsmrf.tsmrf_management.model.SalesOrder;
import com.tsmrf.tsmrf_management.repository.SalesOrderRepository;

@Service
public class SalesOrderService {

    private final SalesOrderRepository repository;

    public SalesOrderService(SalesOrderRepository repository) {
        this.repository = repository;
    }

    public SalesOrder create(SalesOrder order) {
        if (order.getOrderNumber() == null
                || order.getOrderNumber().isBlank()) {
            throw new RuntimeException("Order number is required");
        }

        if (order.getCustomerName() == null
                || order.getCustomerName().isBlank()) {
            throw new RuntimeException("Customer name is required");
        }

        if (order.getProductName() == null
                || order.getProductName().isBlank()) {
            throw new RuntimeException("Product name is required");
        }

        if (order.getOrderedWeight() == null
                || order.getOrderedWeight().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Ordered weight must be positive");
        }

        if (order.getUnitPrice() == null
                || order.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Unit price must be positive");
        }

        order.setDeliveredWeight(BigDecimal.ZERO);
        order.setOrderDate(LocalDate.now());
        order.setStatus("CONFIRMED");

        return repository.save(order);
    }

    public List<SalesOrder> getAll() {
        return repository.findAll();
    }

    public SalesOrder getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Sales order not found"));
    }

    public SalesOrder updateDelivery(Long id, BigDecimal deliveredWeight) {
        SalesOrder order = getById(id);

        if (!"CONFIRMED".equals(order.getStatus())) {
            throw new RuntimeException(
                    "Only confirmed orders can be delivered");
        }

        if (deliveredWeight == null
                || deliveredWeight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException(
                    "Delivered weight must be positive");
        }

        if (deliveredWeight.compareTo(order.getOrderedWeight()) > 0) {
            throw new RuntimeException(
                    "Delivered weight cannot exceed ordered weight");
        }

        order.setDeliveredWeight(deliveredWeight);
        order.setStatus("DELIVERED");

        return repository.save(order);
    }
}