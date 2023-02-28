package com.harera.hayat.donations.stubs.city;

import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.city.State;
import com.harera.hayat.framework.repository.city.CityRepository;
import com.harera.hayat.framework.repository.city.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StateStubs {

    @Autowired
    private StateRepository repository;

    public State insert(String arabicName, String englishName) {
        State state = create(arabicName, englishName);
        repository.save(state);
        return state;
    }

    private State create(String arabicName, String englishName) {
        State state = new State();
        state.setArabicName(arabicName);
        state.setEnglishName(englishName);
        return state;
    }
}
