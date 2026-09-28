package com.tsmrf.tsmrf_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsmrf.tsmrf_management.model.Budget;

public interface BudgetRepository
        extends JpaRepository<Budget, Long> {
}