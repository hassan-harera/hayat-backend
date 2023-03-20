package com.harera.hayat.donations.model.medicine;

import com.harera.hayat.donations.model.Donation;
import com.harera.hayat.framework.model.medicine.Medicine;
import com.harera.hayat.framework.model.medicine.MedicineUnit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Setter
@Getter
@Entity
@Table(name = "medicine_donation")
public class MedicineDonation extends Donation {

    @Column(name = "quantity")
    private Float quantity;

    @ManyToOne
    @JoinColumn(name = "medicine_unit_id", referencedColumnName = "id")
    private MedicineUnit medicineUnit;

    @Column(name = "medicine_expiration_date")
    private OffsetDateTime medicineExpirationDate;

    @ManyToOne
    @JoinColumn(name = "medicine_id", referencedColumnName = "id")
    private Medicine medicine;
}
