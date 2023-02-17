package com.harera.hayat.needs.stubs;

import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.repository.city.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CityStubs {

    @Autowired
    private CityRepository repository;

    public City insert(String arabicName, String englishName) {
        City city = create(arabicName, englishName);
        repository.save(city);
        return city;
    }

    private City create(String arabicName, String englishName) {
        City city = new City();
        city.setArabicName(arabicName);
        city.setEnglishName(englishName);
        return city;
    }
}
