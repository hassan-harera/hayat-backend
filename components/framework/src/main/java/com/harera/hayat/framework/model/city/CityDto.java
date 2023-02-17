package com.harera.hayat.framework.model.city;

import com.harera.hayat.framework.model.BaseEntityDto;

import lombok.Data;

@Data
public class CityDto extends BaseEntityDto {

    private String arabicName;
    private String englishName;
}
