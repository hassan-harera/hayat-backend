package com.harera.hayat.donations.model.medicine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = { "active", })
public class MedicineDonationResponse extends MedicineDonationDto {

}
