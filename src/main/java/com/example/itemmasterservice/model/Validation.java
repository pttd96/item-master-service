package com.example.itemmasterservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
public class Validation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemClass;
    private String subclass;
    private String department;
    private Double maxThreshold;
    private Double minThreshold;
    private String status;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedTime;

    public Validation() {}

    public Validation(String itemClass, String subclass, String department, Double maxThreshold, Double minThreshold, String status) {
        this.itemClass = itemClass;
        this.subclass = subclass;
        this.department = department;
        this.maxThreshold = maxThreshold;
        this.minThreshold = minThreshold;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemClass() {
        return itemClass;
    }

    public void setItemClass(String itemClass) {
        this.itemClass = itemClass;
    }

    public String getSubclass() {
        return subclass;
    }

    public void setSubclass(String subclass) {
        this.subclass = subclass;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getMaxThreshold() {
        return maxThreshold;
    }

    public void setMaxThreshold(Double maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    public Double getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(Double minThreshold) {
        this.minThreshold = minThreshold;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}
