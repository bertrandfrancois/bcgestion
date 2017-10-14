package com.enums;

public enum Unit {

    FF("ff");

    private String unit;

    Unit(String unit) {

        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}
