package com.harera.hayat.needs.controller.possession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.needs.ApplicationIT;
import com.harera.hayat.needs.model.CommunicationMethod;
import com.harera.hayat.needs.model.NeedCategory;
import com.harera.hayat.needs.model.NeedStatus;
import com.harera.hayat.needs.model.possession.PossessionCategory;
import com.harera.hayat.needs.model.possession.PossessionCondition;
import com.harera.hayat.needs.model.possession.PossessionNeedRequest;
import com.harera.hayat.needs.model.possession.PossessionNeedResponse;
import com.harera.hayat.needs.stubs.CityStubs;
import com.harera.hayat.needs.stubs.possession.PossessionCategoryStubs;
import com.harera.hayat.needs.stubs.possession.PossessionConditionStubs;
import com.harera.hayat.needs.stubs.possession.PossessionNeedStubs;
import com.harera.hayat.needs.util.DataUtil;
import com.harera.hayat.needs.util.RequestUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class PossessionNeedControllerIT extends ApplicationIT {

    private final RequestUtil requestUtil;
    private final PossessionNeedStubs possessionNeedStubs;
    private final CityStubs cityStubs;
    private final PossessionConditionStubs possessionCondition;
    private final PossessionCategoryStubs possessionCategory;
    private final DataUtil dataUtil;

    @Test
    void create_thenValidate() {
        City city = cityStubs.insert("arabicName", "englishName");

        PossessionCategory category = possessionCategory.insert("categoryName", "categoryDescription");
        PossessionCondition condition = possessionCondition.insert("conditionName", "conditionDescription");

        PossessionNeedRequest possessionNeedRequest = new PossessionNeedRequest();
        possessionNeedRequest.setTitle("title");
        possessionNeedRequest.setDescription("description");
        possessionNeedRequest.setCommunicationMethod(CommunicationMethod.PHONE);
        possessionNeedRequest.setCityId(city.getId());
        possessionNeedRequest.setPossessionCategoryId(category.getId());
        possessionNeedRequest.setPossessionConditionId(condition.getId());

        PossessionNeedResponse body = null;
        try {
            ResponseEntity<PossessionNeedResponse> bookNeedResponseResponseEntity
                    = requestUtil.post("/api/v1/needs/possession", possessionNeedRequest, null, PossessionNeedResponse.class);

            body = bookNeedResponseResponseEntity.getBody();

            assertEquals(200, bookNeedResponseResponseEntity.getStatusCode().value());
            assertNotNull(body);
            assertEquals(NeedCategory.POSSESSION, body.getCategory());
            assertEquals(NeedStatus.PENDING, body.getStatus());
            assertEquals(possessionNeedRequest.getTitle(), body.getTitle());
            assertEquals(possessionNeedRequest.getDescription(), body.getDescription());
            assertNotNull(body.getNeedDate());
            assertNotNull(body.getNeedExpirationDate());
            assertEquals(possessionNeedRequest.getCommunicationMethod(), body.getCommunicationMethod());
            assertEquals(possessionNeedRequest.getCityId(), body.getCity().getId());
            assertEquals(possessionNeedRequest.getPossessionCategoryId(), body.getPossessionCategory().getId());
            assertEquals(possessionNeedRequest.getPossessionConditionId(), body.getPossessionCondition().getId());
        } finally {
            dataUtil.delete(possessionNeedStubs.get(body.getId()));
        }
    }
}
