package com.tsmrf.tsmrf_management.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsmrf.tsmrf_management.model.Account;
import com.tsmrf.tsmrf_management.model.JournalEntry;
import com.tsmrf.tsmrf_management.repository.AccountRepository;
import com.tsmrf.tsmrf_management.repository.JournalEntryRepository;
import com.tsmrf.tsmrf_management.repository.JournalRepository;

@Service
public class JournalEntryService {

    private final JournalEntryRepository entryRepository;
    private final JournalRepository journalRepository;
    private final AccountRepository accountRepository;

    public JournalEntryService(
            JournalEntryRepository entryRepository,
            JournalRepository journalRepository,
            AccountRepository accountRepository) {
        this.entryRepository = entryRepository;
        this.journalRepository = journalRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public List<JournalEntry> createEntries(
            List<JournalEntry> entries) {

        if (entries == null || entries.size() < 2) {
            throw new IllegalArgumentException(
                    "At least two ledger lines are required");
        }

        BigDecimal totalDebit = BigDecimal.ZERO;
        BigDecimal totalCredit = BigDecimal.ZERO;

        String transactionId = entries.get(0).getTransactionId();
        String journalType = entries.get(0).getJournalType();

        if (transactionId == null || transactionId.isBlank()
                || journalType == null) {
            throw new IllegalArgumentException(
                    "Transaction ID and journal type are required");
        }

        journalType = journalType.toUpperCase();

        if (!Set.of("SALES", "PURCHASE", "CASH", "BANK")
                .contains(journalType)) {
            throw new IllegalArgumentException(
                    "Invalid journal type");
        }

        if (journalRepository.findByJournalType(journalType)
                .isEmpty()) {
            throw new IllegalArgumentException(
                    "Create the journal before posting entries");
        }

        for (JournalEntry entry : entries) {
            if (!transactionId.equals(entry.getTransactionId())) {
                throw new IllegalArgumentException(
                        "All entries must have the same transaction ID");
            }

            if (entry.getTransactionDate() == null ||
                    entry.getAccountCode() == null ||
                    entry.getAccountCode().isBlank()) {
                throw new IllegalArgumentException(
                        "Date and account code are required");
            }

            Account account = accountRepository
                    .findByAccountCode(entry.getAccountCode())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Account not found: " +
                                    entry.getAccountCode()));

            if (!account.isActive()) {
                throw new IllegalArgumentException(
                        "Account is inactive: " +
                                entry.getAccountCode());
            }

            BigDecimal debit = entry.getDebit();
            BigDecimal credit = entry.getCredit();

            if (debit == null || credit == null ||
                    debit.signum() < 0 || credit.signum() < 0 ||
                    (debit.signum() > 0 && credit.signum() > 0) ||
                    (debit.signum() == 0 && credit.signum() == 0)) {
                throw new IllegalArgumentException(
                        "Each line must have either a positive debit " +
                        "or a positive credit");
            }

            entry.setJournalType(journalType);
            totalDebit = totalDebit.add(debit);
            totalCredit = totalCredit.add(credit);
        }

        if (totalDebit.compareTo(totalCredit) != 0) {
            throw new IllegalArgumentException(
                    "Unbalanced transaction. Debit: " +
                            totalDebit + ", Credit: " + totalCredit);
        }

        if (entryRepository.findByTransactionId(transactionId)
                .size() > 0) {
            throw new IllegalArgumentException(
                    "Transaction ID already exists");
        }

        return entryRepository.saveAll(entries);
    }

    public List<JournalEntry> getAll() {
        return entryRepository.findAll();
    }

    public List<JournalEntry> getByTransaction(String transactionId) {
        return entryRepository.findByTransactionId(transactionId);
    }
}