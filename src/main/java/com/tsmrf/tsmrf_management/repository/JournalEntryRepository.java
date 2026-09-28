package com.tsmrf.tsmrf_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsmrf.tsmrf_management.model.JournalEntry;

public interface JournalEntryRepository
        extends JpaRepository<JournalEntry, Long> {

    List<JournalEntry> findByTransactionId(String transactionId);

    List<JournalEntry> findByJournalType(String journalType);
}