package com.stock.entity.ui;

import java.util.UUID;

public class ProductStatistics {

    private final UUID id;
    private final String name;
    private final String picture;
    private final Statistics sell;
    private final Statistics buy;

    public ProductStatistics(
            UUID id,
            String name,
            String picture,
            double sellPrice,
            long sellVolume,
            double buyPrice,
            long buyVolume
    ) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.sell = new Statistics(sellPrice, sellVolume);
        this.buy = new Statistics(buyPrice, buyVolume);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public Statistics getSell() {
        return sell;
    }

    public Statistics getBuy() {
        return buy;
    }

    private static class Statistics {
        private final double price;
        private final long volume;

        public Statistics(double price, long volume) {
            this.price = price;
            this.volume = volume;
        }

        public double getPrice() {
            return price;
        }

        public long getVolume() {
            return volume;
        }

    }
}
