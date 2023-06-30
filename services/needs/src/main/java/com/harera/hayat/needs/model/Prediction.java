package com.harera.hayat.needs.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Prediction implements Serializable {

    private String label;
    private double certainty;
}
