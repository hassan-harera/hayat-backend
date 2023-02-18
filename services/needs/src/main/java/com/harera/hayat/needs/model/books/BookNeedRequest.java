package com.harera.hayat.needs.model.books;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = { "id", "user", "city", "category",
        "status", "image_url", "need_date", "need_expiration_date" })
public class BookNeedRequest extends BookNeedDto {

}
