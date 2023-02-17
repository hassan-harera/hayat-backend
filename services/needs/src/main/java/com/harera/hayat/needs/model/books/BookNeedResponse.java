package com.harera.hayat.needs.model.books;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = { "user_id", "city_id" })
public class BookNeedResponse extends BookNeedDto {

}
