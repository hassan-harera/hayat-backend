package com.harera.hayat.needs.model.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true, value = { "user_id", "city_id" })
public class BookNeedResponse extends BookNeedDto {

}
