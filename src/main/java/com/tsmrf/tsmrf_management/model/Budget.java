package com.tsmrf.tsmrf_management.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long analyticAccountId;

    private String period;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(precision = 14, scale = 2)
    private BigDecimal plannedElectricityCost = BigDecimal.ZERO;

    @Column(precision = 14, scale = 2)
    private BigDecimal actualElectricityCost = BigDecimal.ZERO;

    @Column(precision = 14, scale = 2)
    private BigDecimal plannedRepairCost = BigDecimal.ZERO;

    @Column(precision = 14, scale = 2)
    private BigDecimal actualRepairCost = BigDecimal.ZERO;

    @Column(precision = 14, scale = 2)
    private BigDecimal plannedSales = BigDecimal.ZERO;

    @Column(precision = 14, scale = 2)
    private BigDecimal actualSales = BigDecimal.ZERO;

    public Budget() {
    }

    public Long getId() {
        return id;
    }

    public Long getAnalyticAccountId() {
        return analyticAccountId;
    }

    public void setAnalyticAccountId(Long analyticAccountId) {
        this.analyticAccountId = analyticAccountId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPlannedElectricityCost() {
        return plannedElectricityCost;
    }

    public void setPlannedElectricityCost(BigDecimal value) {
        this.plannedElectricityCost = value;
    }

    public BigDecimal getActualElectricityCost() {
        return actualElectricityCost;
    }

    public void setActualElectricityCost(BigDecimal value) {
        this.actualElectricityCost = value;
    }

    public BigDecimal getPlannedRepairCost() {
        return plannedRepairCost;
    }

    public void setPlannedRepairCost(BigDecimal value) {
        this.plannedRepairCost = value;
    }

    public BigDecimal getActualRepairCost() {
        return actualRepairCost;
    }

    public void setActualRepairCost(BigDecimal value) {
        this.actualRepairCost = value;
    }

    public BigDecimal getPlannedSales() {
        return plannedSales;
    }

    public void setPlannedSales(BigDecimal value) {
        this.plannedSales = value;
    }

    public BigDecimal getActualSales() {
        return actualSales;
    }

    public void setActualSales(BigDecimal value) {
        this.actualSales = value;
    }
}