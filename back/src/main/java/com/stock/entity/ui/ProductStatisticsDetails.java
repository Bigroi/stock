package com.stock.entity.ui;

import java.util.List;
import java.util.UUID;

public class ProductStatisticsDetails {

    private final UUID id;
    private final String name;
    private final String picture;
    private final List<DetailsRow> rows;

    public ProductStatisticsDetails(UUID id, String name, String picture, List<DetailsRow> rows) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.rows = rows;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<DetailsRow> getRows() {
        return rows;
    }

    public String getPicture() {
        return picture;
    }

    public static class DetailsRow {
        private final String price;
        private final int lotVolume;
        private final int tenderVolume;

        public DetailsRow(String price, int lotVolume, int tenderVolume) {
            this.price = price;
            this.lotVolume = lotVolume;
            this.tenderVolume = tenderVolume;
        }

        public String getPrice() {
            return price;
        }

        public int getLotVolume() {
            return lotVolume;
        }

        public int getTenderVolume() {
            return tenderVolume;
        }
    }
}
