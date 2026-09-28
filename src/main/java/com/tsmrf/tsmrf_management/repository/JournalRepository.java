package com.tsmrf.tsmrf_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsmrf.tsmrf_management.model.Journal;

public interface JournalRepository
        extends JpaRepository<Journal, Long> {

    Optional<Journal> findByJournalType(String journalType);
}