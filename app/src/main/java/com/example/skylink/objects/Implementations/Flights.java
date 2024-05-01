package com.example.skylink.objects.Implementations;

import com.example.skylink.objects.Interfaces.iFlight;
import com.example.skylink.objects.Interfaces.iFlights;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Flights implements iFlights {
    private HashMap<String, List<List<List<iFlight>>>> data;

    public Flights(HashMap<String, List<List<List<iFlight>>>> data) {
        this.data = data;
    }
    public Flights(){

    };

    public HashMap<String, List<List<List<iFlight>>>> getData() {
        return data;
    }
}
