package com.example.itemmasterservice.dto;

import com.example.itemmasterservice.model.Buyer;


public class ItemUpdateResult {

    public enum Status {
        SUCCESS,
        PENDING_APPROVAL
    }

    private Status status;
    private String message;
    private String buyerEmail; // Nullable

    public ItemUpdateResult() {}

    public ItemUpdateResult(Status status, String message, String buyerEmail) {
        this.status = status;
        this.message = message;
        this.buyerEmail = buyerEmail;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }
}