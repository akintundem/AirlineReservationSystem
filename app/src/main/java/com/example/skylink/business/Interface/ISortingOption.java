package com.example.skylink.business.Interface;

import com.example.skylink.objects.Interfaces.iFlight;

import java.util.List;

public interface ISortingOption {
    int compare(List<List<iFlight>> flightList1, List<List<iFlight>> flightList2);
}
