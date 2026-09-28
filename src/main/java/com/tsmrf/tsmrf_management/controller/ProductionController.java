
package com.tsmrf.tsmrf_management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsmrf.tsmrf_management.model.Production;
import com.tsmrf.tsmrf_management.service.ProductionService;

@RestController
@RequestMapping("/api/mrf/production")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @PostMapping
    public ResponseEntity<Production> createProduction(
            @RequestBody Production production) {
        return ResponseEntity.ok(
                productionService.createProduction(production));
    }

    @GetMapping
    public List<Production> getAllProductions() {
        return productionService.getAllProductions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Production> getProductionById(
            @PathVariable Long id) {
        return productionService.getProductionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Production> updateProduction(
            @PathVariable Long id,
            @RequestBody Production production) {
        return productionService.updateProduction(id, production)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduction(
            @PathVariable Long id) {
        if (productionService.deleteProduction(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}