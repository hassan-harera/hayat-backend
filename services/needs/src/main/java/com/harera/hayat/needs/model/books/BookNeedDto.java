package com.harera.hayat.needs.model.books;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.needs.model.NeedDto;

import lombok.Data;

@Data
public class BookNeedDto extends NeedDto {

    @JsonProperty(value = "book_name")
    private String bookName;

    @JsonProperty(value = "book_author")
    private String bookAuthor;

    @JsonProperty(value = "book_publisher")
    private String bookPublisher;

    @JsonProperty(value = "book_language")
    private String bookLanguage;
}
