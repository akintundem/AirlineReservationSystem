package com.example.skylink.business.Implementations;

import com.example.skylink.business.Interface.ISortingOption;
import com.example.skylink.objects.Interfaces.iFlight;

import java.util.List;

public class SortingByPrice implements ISortingOption {
    @Override
    public int compare(List<List<iFlight>> flightList1, List<List<iFlight>> flightList2) {
        int price1 = getTotalPrice(flightList1);
        int price2 = getTotalPrice(flightList2);
        return Integer.compare(price1, price2);
    }

    private int getTotalPrice (List<List<iFlight>> flightList) {
        int totalPrice = 0;
        if (!flightList.isEmpty() && !flightList.get(0).isEmpty()) {
            totalPrice += flightList.get(0).get(0).getEconPrice();
        }

        return totalPrice;

    }

}
