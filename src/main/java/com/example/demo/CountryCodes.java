package com.example.demo;

public enum CountryCodes {
    TW("TW"),
    JP("JP"),
    US("US");

    private final String country;

    CountryCodes(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }
}
