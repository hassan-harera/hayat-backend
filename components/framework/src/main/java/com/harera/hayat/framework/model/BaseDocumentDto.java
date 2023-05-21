package com.harera.hayat.framework.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
public class BaseDocumentDto {

    @Id
    private String id;

    @JsonProperty(value = "active")
    private boolean active = true;
}
