
package com.tsmrf.tsmrf_management.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tsmrf.tsmrf_management.model.Invoice;
import com.tsmrf.tsmrf_management.service.InvoiceService;

@RestController
@RequestMapping("/api/mrf/invoices")
public class InvoiceController {

    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    // Create invoice
    @PostMapping
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        return service.create(invoice);
    }

    // Get all invoices
    @GetMapping
    public List<Invoice> getAllInvoices() {
        return service.getAll();
    }

    // Get invoice by ID
    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable Long id) {
        return service.getById(id);
    }

    // Update invoice
    @PutMapping("/{id}")
    public Invoice updateInvoice(
            @PathVariable Long id,
            @RequestBody Invoice invoice) {
        return service.update(id, invoice);
    }

    // Update invoice status
    @PutMapping("/{id}/status")
    public Invoice updateInvoiceStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return service.updateStatus(id, status);
    }

    // Delete invoice
    @DeleteMapping("/{id}")
    public String deleteInvoice(@PathVariable Long id) {
        service.delete(id);
        return "Invoice deleted successfully";
    }
}