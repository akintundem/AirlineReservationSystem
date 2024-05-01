package com.example.skylink.persistence.Implementations;

import com.example.skylink.objects.Implementations.City;
import com.example.skylink.objects.Interfaces.iCity;
import com.example.skylink.persistence.Interfaces.iCitiesRepository;

import java.util.ArrayList;
import java.util.List;

public class CitiesRepository implements iCitiesRepository {
    public List<iCity> getCities() {
        List<iCity> cities = new ArrayList<>();
        cities.add(new City("Toronto", "YYZ"));
        cities.add(new City("Montreal", "YUL"));
        cities.add(new City("Calgary", "YYC"));
        cities.add(new City("Ottawa", "YOW"));
        cities.add(new City("Edmonton", "YEG"));
        cities.add(new City("Mississauga", "YYZ"));
        cities.add(new City("Winnipeg", "YWG"));
        cities.add(new City("Vancouver", "YVR"));
        cities.add(new City("Brampton", "YZZ"));
        cities.add(new City("Hamilton", "YHM"));
        return cities;
    }
}
