package com.tsmrf.tsmrf_management.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tsmrf.tsmrf_management.model.Budget;
import com.tsmrf.tsmrf_management.repository.AnalyticAccountRepository;
import com.tsmrf.tsmrf_management.repository.BudgetRepository;

@Service
public class BudgetService {

    private final BudgetRepository repository;
    private final AnalyticAccountRepository accountRepository;

    public BudgetService(
            BudgetRepository repository,
            AnalyticAccountRepository accountRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
    }

    public Budget create(Budget budget) {
        validate(budget);
        return repository.save(budget);
    }

    public List<Budget> getAll() {
        return repository.findAll();
    }

    public Budget getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Budget not found"));
    }

    public Budget update(Long id, Budget updated) {
        Budget budget = getById(id);

        if (updated.getAnalyticAccountId() != null) {
            budget.setAnalyticAccountId(
                updated.getAnalyticAccountId());
        }

        if (updated.getPeriod() != null) {
            budget.setPeriod(updated.getPeriod());
        }

        if (updated.getStartDate() != null) {
            budget.setStartDate(updated.getStartDate());
        }

        if (updated.getEndDate() != null) {
            budget.setEndDate(updated.getEndDate());
        }

        if (updated.getPlannedElectricityCost() != null) {
            budget.setPlannedElectricityCost(
                updated.getPlannedElectricityCost());
        }

        if (updated.getActualElectricityCost() != null) {
            budget.setActualElectricityCost(
                updated.getActualElectricityCost());
        }

        if (updated.getPlannedRepairCost() != null) {
            budget.setPlannedRepairCost(
                updated.getPlannedRepairCost());
        }

        if (updated.getActualRepairCost() != null) {
            budget.setActualRepairCost(
                updated.getActualRepairCost());
        }

        if (updated.getPlannedSales() != null) {
            budget.setPlannedSales(updated.getPlannedSales());
        }

        if (updated.getActualSales() != null) {
            budget.setActualSales(updated.getActualSales());
        }

        validate(budget);
        return repository.save(budget);
    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }

    private void validate(Budget budget) {
        if (budget.getAnalyticAccountId() == null ||
            !accountRepository.existsById(
                budget.getAnalyticAccountId())) {
            throw new IllegalArgumentException(
                "Valid analytic account ID is required");
        }

        if (budget.getStartDate() != null &&
            budget.getEndDate() != null &&
            budget.getEndDate().isBefore(budget.getStartDate())) {
            throw new IllegalArgumentException(
                "End date cannot be before start date");
        }

        checkAmount(budget.getPlannedElectricityCost());
        checkAmount(budget.getActualElectricityCost());
        checkAmount(budget.getPlannedRepairCost());
        checkAmount(budget.getActualRepairCost());
        checkAmount(budget.getPlannedSales());
        checkAmount(budget.getActualSales());
    }

    private void checkAmount(BigDecimal amount) {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                "Budget amounts cannot be negative");
        }
    }
}