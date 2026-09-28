package com.tsmrf.tsmrf_management.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsmrf.tsmrf_management.model.SalesOrder;
import com.tsmrf.tsmrf_management.service.SalesOrderService;

@RestController
@RequestMapping("/api/mrf/sales-orders")
public class SalesOrderController {

    private final SalesOrderService service;

    public SalesOrderController(SalesOrderService service) {
        this.service = service;
    }

    @PostMapping
    public SalesOrder create(@RequestBody SalesOrder order) {
        return service.create(order);
    }

    @GetMapping
    public List<SalesOrder> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public SalesOrder getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}/delivery")
    public SalesOrder updateDelivery(
            @PathVariable Long id,
            @RequestBody DeliveryRequest request) {
        return service.updateDelivery(id, request.getDeliveredWeight());
    }

    public static class DeliveryRequest {
        private BigDecimal deliveredWeight;

        public BigDecimal getDeliveredWeight() {
            return deliveredWeight;
        }

        public void setDeliveredWeight(BigDecimal deliveredWeight) {
            this.deliveredWeight = deliveredWeight;
        }
    }
}