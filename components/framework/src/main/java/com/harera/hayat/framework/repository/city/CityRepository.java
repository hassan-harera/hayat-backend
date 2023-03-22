package com.harera.hayat.framework.repository.city;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.harera.hayat.framework.model.city.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("select e from City e where e.state.id = :stateId")
    List<City> findByStateId(long stateId);

    @Query("select e from City e where e.arabicName like %?1% or e.englishName like %?1%")
    List<City> search(String query);
}
