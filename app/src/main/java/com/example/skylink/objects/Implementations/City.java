package com.example.skylink.objects.Implementations;

import com.example.skylink.objects.Interfaces.iCity;

public class City implements iCity {
    private String name;
    private String code;

    // Constructor
    public City(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public City(){

    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return name + " - " + code;
    }
}
