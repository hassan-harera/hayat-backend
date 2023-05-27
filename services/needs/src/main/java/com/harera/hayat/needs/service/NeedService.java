package com.harera.hayat.needs.service;

import com.harera.hayat.needs.model.NeedDto;
import com.harera.hayat.needs.model.blood.BloodNeedResponse;
import com.harera.hayat.needs.model.book.BookNeedResponse;
import com.harera.hayat.needs.model.medicine.MedicineNeedResponse;
import com.harera.hayat.needs.service.blood.BloodNeedService;
import com.harera.hayat.needs.service.book.BookNeedService;
import com.harera.hayat.needs.service.medicine.MedicineNeedService;
import com.harera.hayat.needs.service.possession.PossessionNeedService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class NeedService {

    private final BookNeedService needService;
    private final MedicineNeedService medicineNeedService;
    private final BloodNeedService bloodNeedService;
    private final PossessionNeedService possessionNeedService;

    public NeedService(MongoTemplate mongoTemplate, BookNeedService needService,
                    MedicineNeedService medicineNeedService,
                    BloodNeedService bloodNeedService,
                    PossessionNeedService possessionNeedService) {
        this.needService = needService;
        this.medicineNeedService = medicineNeedService;
        this.bloodNeedService = bloodNeedService;
        this.possessionNeedService = possessionNeedService;
    }

    public List<NeedDto> search(String query, int page) {
        List<BookNeedResponse> bookNeedResponses = needService.search(query, page, 6);
        List<MedicineNeedResponse> medicineNeedResponses =
                        medicineNeedService.search(query, page, 6);
        List<BloodNeedResponse> bloodNeedResponses =
                        bloodNeedService.search(query, page, 6);

        List<NeedDto> needResponses = new LinkedList<>();
        needResponses.addAll(bookNeedResponses);
        needResponses.addAll(medicineNeedResponses);
        needResponses.addAll(bloodNeedResponses);
        Collections.shuffle(needResponses);
        return needResponses;
    }
}
