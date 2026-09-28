package com.tsmrf.tsmrf_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsmrf.tsmrf_management.model.AnalyticAccount;

public interface AnalyticAccountRepository
        extends JpaRepository<AnalyticAccount, Long> {
}