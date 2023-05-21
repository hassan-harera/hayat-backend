package com.harera.hayat.needs.model;

import java.time.LocalDateTime;

import com.harera.hayat.framework.model.BaseDocument;
import com.harera.hayat.framework.model.city.CityDto;

import com.harera.hayat.framework.model.user.BaseUserDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
public class Need extends BaseDocument {

    @Field(name = "title")
    private String title;

    @Field(name = "description")
    private String description;

    @Field(name = "need_date")
    private LocalDateTime needDate;

    @Field(name = "need_expiration_date")
    private LocalDateTime needExpirationDate;

    @Field(name = "category")
    private NeedCategory category;

    @Field(name = "status")
    private NeedStatus status;

    @Field(name = "communication_method")
    private CommunicationMethod communicationMethod;

    @Field(name = "image_url")
    private String imageUrl;

    @Field(name = "city")
    private CityDto city;

    @Field(name = "user")
    private BaseUserDto user;
}
