package com.example.ordernode.entity;

public enum TypeEnum {
    ORDERED("ordered"), ACTUAL("actual");

    private final String type;

    TypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
