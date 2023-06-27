package com.harera.hayat.donations.model.ai;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Prediction implements Serializable {

    private String label;
    private double certainty;
}
