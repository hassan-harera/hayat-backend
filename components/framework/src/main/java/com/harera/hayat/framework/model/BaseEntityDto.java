package com.harera.hayat.framework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntityDto implements Serializable {

    private Long id;
    private Boolean active;
}
