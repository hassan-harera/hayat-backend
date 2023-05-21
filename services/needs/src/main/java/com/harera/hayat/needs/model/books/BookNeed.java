package com.harera.hayat.needs.model.books;

import com.harera.hayat.needs.model.Need;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document("book_need")
public class BookNeed extends Need {

    @Field(name = "book_title")
    private String bookTitle;

    @Field(name = "book_sub_title")
    private String bookSubTitle;

    @Field(name = "book_author")
    private String bookAuthor;

    @Field(name = "book_publisher")
    private String bookPublisher;

    @Field(name = "book_publication_year")
    private String bookPublicationYear;

    @Field(name = "book_language")
    private String bookLanguage;
}
