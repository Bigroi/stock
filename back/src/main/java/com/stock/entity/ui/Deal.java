package com.stock.entity.ui;

import com.stock.entity.DealStatus;

import java.util.Date;
import java.util.UUID;

public class Deal {

    private final UUID id;
    private final String productName;
    private final String categoryName;
    private final Date creationDate;
    private final DealStatus status;

    public Deal(UUID id, String productName, String categoryName, Date creationDate, DealStatus status) {
        this.id = id;
        this.productName = productName;
        this.categoryName = categoryName;
        this.creationDate = creationDate;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public DealStatus getStatus() {
        return status;
    }
}
