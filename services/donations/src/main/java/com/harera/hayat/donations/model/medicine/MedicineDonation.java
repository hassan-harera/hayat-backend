package com.harera.hayat.donations.model.medicine;


import com.harera.hayat.donations.model.Donation;
import com.harera.hayat.framework.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
@Entity
@Table(name = "medicine_donation")
public class MedicineDonation extends BaseEntity {

    @Column(name = "amount")
    private Float amount;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private MedicineUnit medicineUnit;

    @Column(name = "medicine_expiration_date")
    private OffsetDateTime medicineExpirationDate;

    @OneToOne
    @JoinColumn(name = "donation_id", referencedColumnName = "id")
    private Donation donation;

    @ManyToOne
    @JoinColumn(name = "medicine_id", referencedColumnName = "id")
    private Medicine medicine;
}
