package com.harera.hayat.framework.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Data;

@Table(name = "global_message")
@Entity
@Data
public class GlobalMessage extends BaseEntity {
    @Column(name = "code_")
    private String code;
    @Column(name = "language_")
    private String language;
    @Column(name = "message_")
    private String message;
}
