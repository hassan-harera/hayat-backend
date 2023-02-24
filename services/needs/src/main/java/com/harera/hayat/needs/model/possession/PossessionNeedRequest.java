package com.harera.hayat.needs.model.possession;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"id", "user", "city", "category",
        "status", "image_url", "need_date", "need_expiration_date", "possession_condition",
        "possession_category"})
public class PossessionNeedRequest extends PossessionNeedDto {

}
