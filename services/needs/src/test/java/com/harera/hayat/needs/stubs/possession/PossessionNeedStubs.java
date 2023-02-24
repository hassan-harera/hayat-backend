package com.harera.hayat.needs.stubs.possession;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.user.User;
import com.harera.hayat.needs.model.CommunicationMethod;
import com.harera.hayat.needs.model.NeedCategory;
import com.harera.hayat.needs.model.NeedState;
import com.harera.hayat.needs.model.possession.PossessionCategory;
import com.harera.hayat.needs.model.possession.PossessionCondition;
import com.harera.hayat.needs.model.possession.PossessionNeed;
import com.harera.hayat.needs.repository.possession.PossessionNeedRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PossessionNeedStubs {

    private final PossessionNeedRepository bookNeedRepository;

    public PossessionNeed insert(
            String title,
            String description,
            LocalDateTime needDate,
            LocalDateTime needExpirationDate,
            NeedCategory category,
            NeedState status,
            CommunicationMethod communicationMethod,
            City city,
            User user,
            PossessionCondition possessionCondition,
            PossessionCategory possessionCategory
    ) {
        PossessionNeed bookNeed = create(
                title,
                description,
                needDate,
                needExpirationDate,
                category,
                status,
                communicationMethod,
                city,
                user,
                possessionCondition,
                possessionCategory
        );
        return bookNeedRepository.save(bookNeed);
    }

    public PossessionNeed create(
            String title,
            String description,
            LocalDateTime needDate,
            LocalDateTime needExpirationDate,
            NeedCategory category,
            NeedState status,
            CommunicationMethod communicationMethod,
            City city,
            User user,
            PossessionCondition possessionCondition,
            PossessionCategory possessionCategory
    ) {
        PossessionNeed bookNeed = new PossessionNeed();
        bookNeed.setTitle(title);
        bookNeed.setDescription(description);
        bookNeed.setNeedDate(needDate);
        bookNeed.setNeedExpirationDate(needExpirationDate);
        bookNeed.setCategory(category);
        bookNeed.setStatus(status);
        bookNeed.setCommunicationMethod(communicationMethod);
        bookNeed.setCity(city);
        bookNeed.setUser(user);
        bookNeed.setPossessionCondition(possessionCondition);
        bookNeed.setPossessionCategory(possessionCategory);
        return bookNeed;
    }

    public PossessionNeed get(Long id) {
        return bookNeedRepository.findById(id).orElse(null);
    }

}
