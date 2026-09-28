package com.tsmrf.tsmrf_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsmrf.tsmrf_management.model.Account;

public interface AccountRepository
        extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountCode(String accountCode);
}