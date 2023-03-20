package com.harera.hayat.framework.service.city;


import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.city.CityResponse;
import com.harera.hayat.framework.repository.city.CityRepository;
import com.harera.hayat.framework.util.ErrorCode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CityService(CityRepository cityRepository,
                       ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    public CityResponse get(long id) {
        City city = cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id),
                ErrorCode.NOT_FOUND_CITY));
        return modelMapper.map(city, CityResponse.class);
    }

    public List<CityResponse> list(long stateId) {
        List<City> cityList;
        if (stateId != 0) {
            cityList = cityRepository.findByStateId(stateId);
        } else {
            cityList = cityRepository.findAll();
        }
        return cityList.stream().map(city -> modelMapper.map(city,
                CityResponse.class)).toList();
    }

    public List<CityResponse> search(String arabicName, String englishName) {
        List<City> cities = cityRepository.search(arabicName, englishName);
        return cities.stream().map(city -> modelMapper.map(city,
                CityResponse.class)).toList();
    }

    public City getCity(long id) {
        return cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id),
                ErrorCode.NOT_FOUND_CITY));
    }
}
