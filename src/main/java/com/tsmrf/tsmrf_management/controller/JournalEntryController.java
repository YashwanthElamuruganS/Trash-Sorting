package com.tsmrf.tsmrf_management.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsmrf.tsmrf_management.model.JournalEntry;
import com.tsmrf.tsmrf_management.service.JournalEntryService;

@RestController
@RequestMapping("/api/mrf/ledger")
@CrossOrigin(origins = "*")
public class JournalEntryController {

    private final JournalEntryService service;

    public JournalEntryController(JournalEntryService service) {
        this.service = service;
    }

    @PostMapping
    public List<JournalEntry> create(
            @RequestBody List<JournalEntry> entries) {
        return service.createEntries(entries);
    }

    @GetMapping
    public List<JournalEntry> getAll() {
        return service.getAll();
    }

    @GetMapping("/{transactionId}")
    public List<JournalEntry> getByTransaction(
            @PathVariable String transactionId) {
        return service.getByTransaction(transactionId);
    }
}