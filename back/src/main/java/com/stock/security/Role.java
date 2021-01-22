package com.stock.security;

public enum Role {
    USER,
    ADMIN;

    public String getSpringName() {
        return "ROLE_" + this.name();
    }

}
