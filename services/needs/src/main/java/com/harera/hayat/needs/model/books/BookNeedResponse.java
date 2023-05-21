package com.harera.hayat.needs.model.books;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.needs.model.NeedDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true, value = { "user_id", "city_id" })
public class BookNeedResponse extends BookNeedDto {

}
