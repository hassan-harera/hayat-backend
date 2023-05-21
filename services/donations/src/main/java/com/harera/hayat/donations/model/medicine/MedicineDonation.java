package com.harera.hayat.donations.model.medicine;

import com.harera.hayat.donations.model.Donation;
import com.harera.hayat.framework.model.medicine.Medicine;
import com.harera.hayat.framework.model.medicine.MedicineUnit;
import com.harera.hayat.framework.model.user.BaseUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private LocalDate medicineExpirationDate;

    @ManyToOne
    @JoinColumn(name = "medicine_id", referencedColumnName = "id")
    private Medicine medicine;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "medicine_donation_upvotes",
                    joinColumns = @JoinColumn(name = "donation_id"),
                    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<BaseUser> upvotes = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "medicine_donation_downvotes",
                    joinColumns = @JoinColumn(name = "donation_id"),
                    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<BaseUser> downvotes = new HashSet<>();

    public Integer getReputation() {
        return upvotes.size() - downvotes.size();
    }

    public void upvote(BaseUser user) {
        upvotes.add(user);
        downvotes.remove(user);
    }

    public void downvote(BaseUser user) {
        downvotes.add(user);
        upvotes.remove(user);
    }
}
