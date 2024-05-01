package com.example.skylink.business.Implementations;

import com.example.skylink.business.Interface.ISortingOption;
import com.example.skylink.objects.Interfaces.iFlight;

import java.util.List;

public class SortingByDirectFlight implements ISortingOption {
    @Override
    public int compare(List<List<iFlight>> flightList1, List<List<iFlight>> flightList2) {
        boolean hasDirectFlight1 = hasDirectFlight(flightList1);
        boolean hasDirectFlight2 = hasDirectFlight(flightList2);
        if (hasDirectFlight1 && !hasDirectFlight2) {
            return -1;
        } else if (!hasDirectFlight1 && hasDirectFlight2) {
            return 1;
        } else {
            return Integer.compare(flightList1.size(), flightList2.size());
        }
    }

    private boolean hasDirectFlight(List<List<iFlight>> flightList) {
        return flightList.size() == 1;
    }
}
