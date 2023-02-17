package com.harera.hayat.framework.model.city;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.harera.hayat.framework.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "city")
public class City extends BaseEntity {

    @Basic
    @Column(name = "arabic_name")
    private String arabicName;
    @Basic
    @Column(name = "english_name")
    private String englishName;

    @ManyToOne
    @JoinColumn(name = "state_id", referencedColumnName = "id")
    private State state;
}
