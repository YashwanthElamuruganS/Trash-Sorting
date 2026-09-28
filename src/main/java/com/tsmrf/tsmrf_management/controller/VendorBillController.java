package com.tsmrf.tsmrf_management.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tsmrf.tsmrf_management.model.VendorBill;
import com.tsmrf.tsmrf_management.service.VendorBillService;

@RestController
@RequestMapping("/api/mrf/vendor-bills")
@CrossOrigin(origins = "*")
public class VendorBillController {

    private final VendorBillService service;

    public VendorBillController(VendorBillService service) {
        this.service = service;
    }

    @PostMapping
    public VendorBill create(@RequestBody VendorBill bill) {
        return service.create(bill);
    }

    @GetMapping
    public List<VendorBill> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public VendorBill getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}/status")
    public VendorBill updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return service.updateStatus(id, status.toUpperCase());
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Vendor bill deleted successfully";
    }
}