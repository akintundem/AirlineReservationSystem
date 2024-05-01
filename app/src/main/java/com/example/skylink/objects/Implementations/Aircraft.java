package com.example.skylink.objects.Implementations;

import com.example.skylink.objects.Interfaces.iAircraft;

public class Aircraft implements iAircraft {
    private String name;
    private int numSeatPerRowBusiness;
    private int numRowsBusiness;
    private int numSeatPerRowEcon;
    private int numRowsEcon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumSeatPerRowBusiness() {
        return numSeatPerRowBusiness;
    }

    public void setNumSeatPerRowBusiness(int numSeatPerRowBusiness) {
        this.numSeatPerRowBusiness = numSeatPerRowBusiness;
    }

    public int getNumRowsBusiness() {
        return numRowsBusiness;
    }

    public void setNumRowsBusiness(int numRowsBusiness) {
        this.numRowsBusiness = numRowsBusiness;
    }

    public int getNumSeatPerRowEcon() {
        return numSeatPerRowEcon;
    }

    public void setNumSeatPerRowEcon(int numSeatPerRowEcon) {
        this.numSeatPerRowEcon = numSeatPerRowEcon;
    }

    public int getNumRowsEcon() {
        return numRowsEcon;
    }

    public void setNumRowsEcon(int numRowsEcon) {
        this.numRowsEcon = numRowsEcon;
    }

    public Aircraft(String name, int numSeatPerRowBusiness, int numRowsBusiness, int numSeatPerRowEcon, int numRowsEcon) {
        this.name = name;
        this.numSeatPerRowBusiness = numSeatPerRowBusiness;
        this.numRowsBusiness = numRowsBusiness;
        this.numSeatPerRowEcon = numSeatPerRowEcon;
        this.numRowsEcon = numRowsEcon;
    }

    public Aircraft(){

    }
}
