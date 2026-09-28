package com.tsmrf.tsmrf_management.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsmrf.tsmrf_management.model.OpticalSorter;
import com.tsmrf.tsmrf_management.service.OpticalSorterService;

@RestController
@RequestMapping("/api/mrf/sorters")
public class OpticalSorterController {

    private final OpticalSorterService opticalSorterService;

    public OpticalSorterController(
            OpticalSorterService opticalSorterService) {

        this.opticalSorterService = opticalSorterService;
    }

    @PostMapping
    public OpticalSorter createSorter(
            @RequestBody OpticalSorter sorter) {

        return opticalSorterService.createSorter(sorter);
    }

    @GetMapping
    public List<OpticalSorter> getAllSorters() {

        return opticalSorterService.getAllSorters();
    }
}