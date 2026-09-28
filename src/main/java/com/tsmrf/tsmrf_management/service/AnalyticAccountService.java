package com.tsmrf.tsmrf_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tsmrf.tsmrf_management.model.AnalyticAccount;
import com.tsmrf.tsmrf_management.repository.AnalyticAccountRepository;

@Service
public class AnalyticAccountService {

    private final AnalyticAccountRepository repository;

    public AnalyticAccountService(AnalyticAccountRepository repository) {
        this.repository = repository;
    }

    public AnalyticAccount create(AnalyticAccount account) {
        return repository.save(account);
    }

    public List<AnalyticAccount> getAll() {
        return repository.findAll();
    }

    public AnalyticAccount getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Analytic account not found"));
    }

    public AnalyticAccount update(Long id, AnalyticAccount updated) {
        AnalyticAccount account = getById(id);

        account.setAccountName(updated.getAccountName());
        account.setDescription(updated.getDescription());
        account.setLocation(updated.getLocation());

        return repository.save(account);
    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }
}