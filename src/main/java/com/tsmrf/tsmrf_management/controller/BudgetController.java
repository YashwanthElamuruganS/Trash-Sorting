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

import com.tsmrf.tsmrf_management.model.Budget;
import com.tsmrf.tsmrf_management.service.BudgetService;

@RestController
@RequestMapping("/api/mrf/budgets")
@CrossOrigin(origins = "*")
public class BudgetController {

    private final BudgetService service;

    public BudgetController(BudgetService service) {
        this.service = service;
    }

    @PostMapping
    public Budget create(@RequestBody Budget budget) {
        return service.create(budget);
    }

    @GetMapping
    public List<Budget> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Budget getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Budget update(
            @PathVariable Long id,
            @RequestBody Budget budget) {
        return service.update(id, budget);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Budget deleted successfully";
    }
}