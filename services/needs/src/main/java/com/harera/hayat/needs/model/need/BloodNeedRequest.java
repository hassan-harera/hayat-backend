package com.harera.hayat.needs.model.need;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties({ "id", "active" })
public class BloodNeedRequest extends BloodNeedDto {

}
