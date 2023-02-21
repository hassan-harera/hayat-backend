package com.harera.hayat.donations.model.image;

import com.harera.hayat.framework.model.BaseEntityDto;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class DonationImageDto extends BaseEntityDto {

    @JoinColumn(name = "url")
    private String imageUrl;
}
