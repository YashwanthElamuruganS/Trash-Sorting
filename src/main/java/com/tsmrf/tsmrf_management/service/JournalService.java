package com.tsmrf.tsmrf_management.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.tsmrf.tsmrf_management.model.Journal;
import com.tsmrf.tsmrf_management.repository.JournalRepository;

@Service
public class JournalService {

    private final JournalRepository repository;

    private static final Set<String> TYPES =
            Set.of("SALES", "PURCHASE", "CASH", "BANK");

    public JournalService(JournalRepository repository) {
        this.repository = repository;
    }

    public Journal create(Journal journal) {
        if (journal.getName() == null ||
                journal.getName().isBlank() ||
                journal.getJournalType() == null) {
            throw new IllegalArgumentException(
                    "Journal name and type are required");
        }

        journal.setJournalType(
                journal.getJournalType().toUpperCase());

        if (!TYPES.contains(journal.getJournalType())) {
            throw new IllegalArgumentException(
                    "Type must be SALES, PURCHASE, CASH or BANK");
        }

        if (repository.findByJournalType(journal.getJournalType())
                .isPresent()) {
            throw new IllegalArgumentException(
                    "Journal type already exists");
        }

        return repository.save(journal);
    }

    public List<Journal> getAll() {
        return repository.findAll();
    }
}