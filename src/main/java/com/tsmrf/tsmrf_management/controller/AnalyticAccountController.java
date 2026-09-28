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
import org.springframework.web.bind.annotation.RestController;

import com.tsmrf.tsmrf_management.model.AnalyticAccount;
import com.tsmrf.tsmrf_management.service.AnalyticAccountService;

@RestController
@RequestMapping("/api/mrf/analytic-accounts")
@CrossOrigin(origins = "*")
public class AnalyticAccountController {

    private final AnalyticAccountService service;

    public AnalyticAccountController(AnalyticAccountService service) {
        this.service = service;
    }

    @PostMapping
    public AnalyticAccount create(@RequestBody AnalyticAccount account) {
        return service.create(account);
    }

    @GetMapping
    public List<AnalyticAccount> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public AnalyticAccount getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public AnalyticAccount update(
            @PathVariable Long id,
            @RequestBody AnalyticAccount account) {
        return service.update(id, account);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Analytic account deleted successfully";
    }
}