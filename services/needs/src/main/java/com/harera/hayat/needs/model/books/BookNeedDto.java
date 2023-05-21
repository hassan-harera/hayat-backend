package com.harera.hayat.needs.model.books;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.needs.model.NeedDto;
import lombok.Data;

@Data
public class BookNeedDto extends NeedDto {

    @JsonProperty(value = "book_title")
    private String bookTitle;

    @JsonProperty(value = "book_sub_title")
    private String bookSubTitle;

    @JsonProperty(value = "book_author")
    private String bookAuthor;

    @JsonProperty(value = "book_publisher")
    private String bookPublisher;

    @JsonProperty(value = "book_publication_year")
    private String bookPublicationYear;

    @JsonProperty(value = "book_language")
    private String bookLanguage;
}
