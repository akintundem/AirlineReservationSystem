package com.example.skylink.objects.Interfaces;

import com.example.skylink.objects.Implementations.Flight;

import java.util.HashMap;
import java.util.List;

public interface iFlights {
    // Getter method
    HashMap<String, List<List<List<iFlight>>>> getData();
}
