package com.tsmrf.tsmrf_management.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.tsmrf.tsmrf_management.model.Account;
import com.tsmrf.tsmrf_management.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository repository;

    private static final Set<String> TYPES =
            Set.of("ASSET", "LIABILITY", "INCOME", "EXPENSE");

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account create(Account account) {
        validate(account);

        if (repository.findByAccountCode(account.getAccountCode())
                .isPresent()) {
            throw new IllegalArgumentException(
                    "Account code already exists");
        }

        return repository.save(account);
    }

    public List<Account> getAll() {
        return repository.findAll();
    }

    public Account getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Account not found: " + id));
    }

    public Account update(Long id, Account updated) {
        Account account = getById(id);

        if (updated.getAccountCode() != null &&
                !updated.getAccountCode().equals(account.getAccountCode()) &&
                repository.findByAccountCode(updated.getAccountCode())
                        .isPresent()) {
            throw new IllegalArgumentException(
                    "Account code already exists");
        }

        if (updated.getAccountCode() != null) {
            account.setAccountCode(updated.getAccountCode());
        }

        if (updated.getAccountName() != null) {
            account.setAccountName(updated.getAccountName());
        }

        if (updated.getAccountType() != null) {
            account.setAccountType(updated.getAccountType());
        }

        account.setDescription(updated.getDescription());
        account.setActive(updated.isActive());

        validate(account);
        return repository.save(account);
    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }

    private void validate(Account account) {
        if (account.getAccountCode() == null ||
                account.getAccountCode().isBlank() ||
                account.getAccountName() == null ||
                account.getAccountName().isBlank() ||
                account.getAccountType() == null ||
                !TYPES.contains(account.getAccountType().toUpperCase())) {
            throw new IllegalArgumentException(
                    "Provide account code, name and a valid type: " +
                    "ASSET, LIABILITY, INCOME or EXPENSE");
        }

        account.setAccountType(account.getAccountType().toUpperCase());
    }
}