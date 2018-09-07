package com.enums;

import lombok.Getter;

@Getter
public enum Unit {

    FF("ff");

    private String unit;

    Unit(String unit) {

        this.unit = unit;
    }

}
