
package com.tsmrf.tsmrf_management.service;

import com.tsmrf.tsmrf_management.model.Production;
import com.tsmrf.tsmrf_management.repository.ProductionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductionService {

    private final ProductionRepository productionRepository;

    public ProductionService(ProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    public Production createProduction(Production production) {
        return productionRepository.save(production);
    }

    public List<Production> getAllProductions() {
        return productionRepository.findAll();
    }

    public Optional<Production> getProductionById(Long id) {
        return productionRepository.findById(id);
    }

    public Optional<Production> updateProduction(
            Long id, Production updatedProduction) {

        return productionRepository.findById(id).map(production -> {
            production.setProductName(updatedProduction.getProductName());
            production.setPlannedQuantity(
                    updatedProduction.getPlannedQuantity());
            production.setProducedQuantity(
                    updatedProduction.getProducedQuantity());
            production.setProductionDate(
                    updatedProduction.getProductionDate());
            production.setStatus(updatedProduction.getStatus());

            return productionRepository.save(production);
        });
    }

    public boolean deleteProduction(Long id) {
        if (!productionRepository.existsById(id)) {
            return false;
        }

        productionRepository.deleteById(id);
        return true;
    }
}