package com.example.itemmasterservice.dto;

import java.time.LocalDateTime;

public class ItemUpdate {
    private String itemClass;
    private String subclass;
    private String department;
    private Double price;
    private Long buyerId;

    // Default constructor
    public ItemUpdate() {}

    // Getters and Setters
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }
}
