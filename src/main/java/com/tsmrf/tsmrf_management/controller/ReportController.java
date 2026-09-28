package com.tsmrf.tsmrf_management.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsmrf.tsmrf_management.service.ReportService;

@RestController
@RequestMapping("/api/mrf/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/balance-sheet")
    public Map<String, Object> getBalanceSheet() {
        return reportService.getBalanceSheet();
    }

    @GetMapping("/profit-loss")
    public Map<String, Object> getProfitLoss() {
        return reportService.getProfitLoss();
    }

    @GetMapping("/budget-variance")
    public List<Map<String, Object>> getBudgetVariance() {
        return reportService.getBudgetVariance();
    }
}