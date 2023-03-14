package com.harera.hayat.needs.model.books;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.framework.model.city.CityDto;
import com.harera.hayat.framework.model.user.UserDto;
import com.harera.hayat.needs.model.CommunicationMethod;
import com.harera.hayat.needs.model.NeedCategory;
import com.harera.hayat.needs.model.NeedState;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true, value = { "user_id", "city_id" })
public class BookNeedResponse {

    @Id
    private String id;

    @Field(name = "active")
    private boolean active = true;

    @JsonProperty(value = "book_name")
    private String bookName;

    @JsonProperty(value = "book_author")
    private String bookAuthor;

    @JsonProperty(value = "book_publisher")
    private String bookPublisher;

    @JsonProperty(value = "book_language")
    private String bookLanguage;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "need_date")
    private LocalDateTime needDate;

    @JsonProperty(value = "need_expiration_date")
    private LocalDateTime needExpirationDate;

    @JsonProperty(value = "category")
    private NeedCategory category;

    @JsonProperty(value = "status")
    private NeedState status;

    @JsonProperty("communication_method")
    private CommunicationMethod communicationMethod;

    @JsonProperty("city")
    private CityDto city;

    @JsonProperty("city_id")
    private Long cityId;

    @JsonProperty("user")
    private UserDto user;
}
