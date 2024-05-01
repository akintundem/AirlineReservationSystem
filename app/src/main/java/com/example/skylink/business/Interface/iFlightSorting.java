package com.example.skylink.business.Interface;

import com.example.skylink.objects.Implementations.Flight;
import com.example.skylink.objects.Interfaces.iFlight;

import java.util.Comparator;
import java.util.List;

public interface iFlightSorting extends Comparator<List<List<iFlight>>> {
    int compare(List<List<iFlight>> flight1, List<List<iFlight>> flight2);
}
