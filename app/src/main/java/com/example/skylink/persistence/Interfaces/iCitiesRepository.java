package com.example.skylink.persistence.Interfaces;

import com.example.skylink.objects.Implementations.City;
import com.example.skylink.objects.Interfaces.iCity;

import java.util.List;

public interface iCitiesRepository {

    List<iCity> getCities();

}
