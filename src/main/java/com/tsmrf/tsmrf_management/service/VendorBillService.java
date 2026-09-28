package com.tsmrf.tsmrf_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tsmrf.tsmrf_management.model.PurchaseOrder;
import com.tsmrf.tsmrf_management.model.VendorBill;
import com.tsmrf.tsmrf_management.repository.PurchaseOrderRepository;
import com.tsmrf.tsmrf_management.repository.VendorBillRepository;

@Service
public class VendorBillService {

    private final VendorBillRepository billRepository;
    private final PurchaseOrderRepository orderRepository;

    public VendorBillService(
            VendorBillRepository billRepository,
            PurchaseOrderRepository orderRepository) {
        this.billRepository = billRepository;
        this.orderRepository = orderRepository;
    }

    public VendorBill create(VendorBill bill) {

        if (bill.getBillNumber() == null
                || bill.getBillNumber().isBlank()) {
            throw new RuntimeException("Bill number is required");
        }

        if (bill.getPurchaseOrderId() == null) {
            throw new RuntimeException("Purchase order ID is required");
        }

        if (billRepository.findByBillNumber(bill.getBillNumber()).isPresent()) {
            throw new RuntimeException("Bill number already exists");
        }

        PurchaseOrder order = orderRepository
                .findById(bill.getPurchaseOrderId())
                .orElseThrow(() -> new RuntimeException(
                        "Purchase order not found"));

        if (billRepository.existsByPurchaseOrderId(order.getId())) {
            throw new RuntimeException(
                    "A bill already exists for this purchase order");
        }

        bill.setVendorName(order.getVendorName());
        bill.setItemDescription(order.getItemDescription());
        bill.setQuantity(order.getQuantity());
        bill.setUnitPrice(order.getUnitPrice());

        if (bill.getBillDate() == null) {
            bill.setBillDate(java.time.LocalDate.now());
        }

        if (bill.getStatus() == null || bill.getStatus().isBlank()) {
            bill.setStatus("UNPAID");
        }

        bill.calculateTotal();
        return billRepository.save(bill);
    }

    public List<VendorBill> getAll() {
        return billRepository.findAll();
    }

    public VendorBill getById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Vendor bill not found"));
    }

    public VendorBill updateStatus(Long id, String status) {
        VendorBill bill = getById(id);

        if (!status.equals("UNPAID")
                && !status.equals("PAID")
                && !status.equals("PARTIALLY_PAID")) {
            throw new RuntimeException("Invalid bill status");
        }

        bill.setStatus(status);
        return billRepository.save(bill);
    }

    public void delete(Long id) {
        VendorBill bill = getById(id);
        billRepository.delete(bill);
    }
}