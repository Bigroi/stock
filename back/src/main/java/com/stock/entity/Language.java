package com.stock.entity;

import java.util.stream.Stream;

public enum Language {
    RU,
    EN,
    PL;

    public static Stream<Language> getStream() {
        return Stream.of(Language.values());
    }
}
