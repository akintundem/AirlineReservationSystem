package com.example.skylink.business.Implementations;

import com.example.skylink.business.Interface.ISortingOption;
import com.example.skylink.business.Interface.iFlightSorting;
import com.example.skylink.objects.Interfaces.iFlight;

import java.util.List;

public class FlightSorting implements iFlightSorting {

    private ISortingOption sortingOption;

    public FlightSorting(ISortingOption sortingOption) {
        this.sortingOption = sortingOption;
    }

    @Override
    public int compare(List<List<iFlight>> flight1, List<List<iFlight>> flight2) {
        return sortingOption.compare(flight1, flight2);

    }

}
