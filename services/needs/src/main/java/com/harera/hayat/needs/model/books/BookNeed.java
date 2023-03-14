package com.harera.hayat.needs.model.books;

import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.user.User;
import com.harera.hayat.needs.model.CommunicationMethod;
import com.harera.hayat.needs.model.NeedCategory;
import com.harera.hayat.needs.model.NeedState;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Setter
@Getter
@Document(collection = "book_need")
public class BookNeed {

    @Id
    private String id;

    @Field(name = "active")
    private boolean active = true;

    @Field(name = "book_name")
    private String bookName;

    @Field(name = "book_author")
    private String bookAuthor;

    @Field(name = "book_publisher")
    private String bookPublisher;

    @Field(name = "book_language")
    private String bookLanguage;

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
    private NeedState status;

    @Field(name = "communication_method")
    private CommunicationMethod communicationMethod;

    @Field(name = "city")
    private City city;

    @Field(name = "user")
    private User user;
}
