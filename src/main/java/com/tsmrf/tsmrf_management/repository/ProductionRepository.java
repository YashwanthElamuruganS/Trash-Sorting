
package com.tsmrf.tsmrf_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsmrf.tsmrf_management.model.Production;

public interface ProductionRepository
        extends JpaRepository<Production, Long> {
}