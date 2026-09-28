package com.tsmrf.tsmrf_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "optical_sorters")
public class OpticalSorter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String manufacturer;

    private String modelNumber;

    private String location;

    private Double conveyorSpeed;

    @Enumerated(EnumType.STRING)
    private SorterStatus status;

    public OpticalSorter() {
    }

    public OpticalSorter(String name,
                         String manufacturer,
                         String modelNumber,
                         String location,
                         Double conveyorSpeed,
                         SorterStatus status) {

        this.name = name;
        this.manufacturer = manufacturer;
        this.modelNumber = modelNumber;
        this.location = location;
        this.conveyorSpeed = conveyorSpeed;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getConveyorSpeed() {
        return conveyorSpeed;
    }

    public void setConveyorSpeed(Double conveyorSpeed) {
        this.conveyorSpeed = conveyorSpeed;
    }

    public SorterStatus getStatus() {
        return status;
    }

    public void setStatus(SorterStatus status) {
        this.status = status;
    }
}