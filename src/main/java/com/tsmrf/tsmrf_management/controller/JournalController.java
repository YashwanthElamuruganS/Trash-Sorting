package com.tsmrf.tsmrf_management.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsmrf.tsmrf_management.model.Journal;
import com.tsmrf.tsmrf_management.service.JournalService;

@RestController
@RequestMapping("/api/mrf/journals")
@CrossOrigin(origins = "*")
public class JournalController {

    private final JournalService service;

    public JournalController(JournalService service) {
        this.service = service;
    }

    @PostMapping
    public Journal create(@RequestBody Journal journal) {
        return service.create(journal);
    }

    @GetMapping
    public List<Journal> getAll() {
        return service.getAll();
    }
}