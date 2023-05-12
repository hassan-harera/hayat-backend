package com.harera.hayat.needs.model.blood;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties({ "id", "active", "user", "city", "category", "status", "image_url",
        "need_date", "need_expiration_date" })
public class BloodNeedRequest extends BloodNeedDto {

}
