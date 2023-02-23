package com.harera.hayat.needs.model.possession;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"user_id", "city_id", "possession_condition_id",
        "possession_category_id"})
public class PossessionNeedResponse extends PossessionNeedDto {

}
