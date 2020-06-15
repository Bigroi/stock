package com.bigroi.stock.bean.db;

import java.util.Objects;

public class Tender extends Bid {

    private String processing;
    private String packaging;

    public String getProcessing() {
        return processing;
    }

    public void setProcessing(String processing) {
        this.processing = processing;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Tender && ((Tender) obj).getId() == this.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
