package com.stock.dao;

import org.jdbi.v3.core.Jdbi;

import java.util.function.Supplier;

public class Transactional {

    private final Jdbi jdbi;

    public Transactional(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public <T> T inTransaction(Supplier<T> supplier) {
        return jdbi.inTransaction(handle -> supplier.get());
    }

    public void useTransaction(Runnable runnable) {
        jdbi.useTransaction(handle -> runnable.run());
    }
}
