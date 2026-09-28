package com.tsmrf.tsmrf_management.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tsmrf.tsmrf_management.model.Account;
import com.tsmrf.tsmrf_management.model.Budget;
import com.tsmrf.tsmrf_management.model.JournalEntry;
import com.tsmrf.tsmrf_management.repository.AccountRepository;
import com.tsmrf.tsmrf_management.repository.BudgetRepository;
import com.tsmrf.tsmrf_management.repository.JournalEntryRepository;

@Service
public class ReportService {

    private final AccountRepository accountRepository;
    private final JournalEntryRepository journalEntryRepository;
    private final BudgetRepository budgetRepository;

    public ReportService(AccountRepository accountRepository,
                         JournalEntryRepository journalEntryRepository,
                         BudgetRepository budgetRepository) {
        this.accountRepository = accountRepository;
        this.journalEntryRepository = journalEntryRepository;
        this.budgetRepository = budgetRepository;
    }

    private BigDecimal zero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    // Balance Sheet
    public Map<String, Object> getBalanceSheet() {
        List<Map<String, Object>> assets = new ArrayList<>();
        List<Map<String, Object>> liabilities = new ArrayList<>();

        BigDecimal totalAssets = BigDecimal.ZERO;
        BigDecimal totalLiabilities = BigDecimal.ZERO;

        List<JournalEntry> entries = journalEntryRepository.findAll();

        for (Account account : accountRepository.findAll()) {
            String type = account.getAccountType().toUpperCase();

            BigDecimal debit = BigDecimal.ZERO;
            BigDecimal credit = BigDecimal.ZERO;

            for (JournalEntry entry : entries) {
                if (account.getAccountCode().equals(entry.getAccountCode())) {
                    debit = debit.add(zero(entry.getDebit()));
                    credit = credit.add(zero(entry.getCredit()));
                }
            }

            Map<String, Object> item = new LinkedHashMap<>();
            item.put("accountCode", account.getAccountCode());
            item.put("accountName", account.getAccountName());

            if ("ASSET".equals(type)) {
                BigDecimal balance = debit.subtract(credit);
                item.put("balance", balance);
                assets.add(item);
                totalAssets = totalAssets.add(balance);
            } else if ("LIABILITY".equals(type)) {
                BigDecimal balance = credit.subtract(debit);
                item.put("balance", balance);
                liabilities.add(item);
                totalLiabilities = totalLiabilities.add(balance);
            }
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("assets", assets);
        result.put("totalAssets", totalAssets);
        result.put("liabilities", liabilities);
        result.put("totalLiabilities", totalLiabilities);
        result.put("netAssets", totalAssets.subtract(totalLiabilities));

        return result;
    }

    // Profit and Loss
    public Map<String, Object> getProfitLoss() {
        List<Map<String, Object>> income = new ArrayList<>();
        List<Map<String, Object>> expenses = new ArrayList<>();

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpenses = BigDecimal.ZERO;

        List<JournalEntry> entries = journalEntryRepository.findAll();

        for (Account account : accountRepository.findAll()) {
            String type = account.getAccountType().toUpperCase();

            BigDecimal debit = BigDecimal.ZERO;
            BigDecimal credit = BigDecimal.ZERO;

            for (JournalEntry entry : entries) {
                if (account.getAccountCode().equals(entry.getAccountCode())) {
                    debit = debit.add(zero(entry.getDebit()));
                    credit = credit.add(zero(entry.getCredit()));
                }
            }

            Map<String, Object> item = new LinkedHashMap<>();
            item.put("accountCode", account.getAccountCode());
            item.put("accountName", account.getAccountName());

            if ("INCOME".equals(type)) {
                BigDecimal balance = credit.subtract(debit);
                item.put("amount", balance);
                income.add(item);
                totalIncome = totalIncome.add(balance);
            } else if ("EXPENSE".equals(type)) {
                BigDecimal balance = debit.subtract(credit);
                item.put("amount", balance);
                expenses.add(item);
                totalExpenses = totalExpenses.add(balance);
            }
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("income", income);
        result.put("totalIncome", totalIncome);
        result.put("expenses", expenses);
        result.put("totalExpenses", totalExpenses);
        result.put("netProfit", totalIncome.subtract(totalExpenses));

        return result;
    }

    // Budget Variance Report
    public List<Map<String, Object>> getBudgetVariance() {
        List<Map<String, Object>> report = new ArrayList<>();

        for (Budget budget : budgetRepository.findAll()) {
            Map<String, Object> item = new LinkedHashMap<>();

            item.put("budgetId", budget.getId());
            item.put("analyticAccountId", budget.getAnalyticAccountId());
            item.put("period", budget.getPeriod());
            item.put("startDate", budget.getStartDate());
            item.put("endDate", budget.getEndDate());

            BigDecimal plannedElectricity =
                    zero(budget.getPlannedElectricityCost());
            BigDecimal actualElectricity =
                    zero(budget.getActualElectricityCost());

            BigDecimal plannedRepair =
                    zero(budget.getPlannedRepairCost());
            BigDecimal actualRepair =
                    zero(budget.getActualRepairCost());

            BigDecimal plannedSales =
                    zero(budget.getPlannedSales());
            BigDecimal actualSales =
                    zero(budget.getActualSales());

            item.put("plannedElectricityCost", plannedElectricity);
            item.put("actualElectricityCost", actualElectricity);
            item.put("electricityVariance",
                    plannedElectricity.subtract(actualElectricity));

            item.put("plannedRepairCost", plannedRepair);
            item.put("actualRepairCost", actualRepair);
            item.put("repairVariance",
                    plannedRepair.subtract(actualRepair));

            item.put("plannedSales", plannedSales);
            item.put("actualSales", actualSales);
            item.put("salesVariance",
                    actualSales.subtract(plannedSales));

            report.add(item);
        }

        return report;
    }
}