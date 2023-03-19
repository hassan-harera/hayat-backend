package com.harera.hayat.notificaions.model.donation;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProcessingDonation {

    private long id;
    private boolean active;
    private Long userId;
}
