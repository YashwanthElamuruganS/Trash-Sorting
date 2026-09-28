package com.tsmrf.tsmrf_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tsmrf.tsmrf_management.model.OpticalSorter;
import com.tsmrf.tsmrf_management.repository.OpticalSorterRepository;

@Service
public class OpticalSorterService {

    private final OpticalSorterRepository opticalSorterRepository;

    public OpticalSorterService(
            OpticalSorterRepository opticalSorterRepository) {

        this.opticalSorterRepository = opticalSorterRepository;
    }

    public OpticalSorter createSorter(OpticalSorter sorter) {
        return opticalSorterRepository.save(sorter);
    }

    public List<OpticalSorter> getAllSorters() {
        return opticalSorterRepository.findAll();
    }
}