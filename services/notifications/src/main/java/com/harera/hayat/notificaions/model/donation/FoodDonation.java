package com.harera.hayat.notificaions.model.donation;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class FoodDonation {

    private long id;
    private boolean active;
    private LocalDateTime expirationDate;
}
