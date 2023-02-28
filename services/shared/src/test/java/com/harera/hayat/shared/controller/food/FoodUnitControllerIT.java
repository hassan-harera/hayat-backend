package com.harera.hayat.shared.controller.food;

import com.harera.hayat.framework.model.food.FoodUnit;
import com.harera.hayat.framework.model.food.FoodUnitResponse;
import com.harera.hayat.shared.ApplicationIT;
import com.harera.hayat.shared.stubs.FoodUnitStubs;
import com.harera.hayat.shared.util.DataUtil;
import com.harera.hayat.shared.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class FoodUnitControllerIT extends ApplicationIT {

    private final RequestUtil requestUtil;
    private final FoodUnitStubs foodUnitStubs;
    private final DataUtil dataUtil;

    @Test
    void list_withEnglishName_thenValidateResponse() {
        FoodUnit unit1 = foodUnitStubs.insert("arabicName", "englishName");
        FoodUnit unit2 = foodUnitStubs.insert("arabicName", "englishName");

        try {
            ResponseEntity<FoodUnitResponse[]> bookNeedResponseResponseEntity =
                            requestUtil.get("/api/v1/food/units", null, null,
                                            FoodUnitResponse[].class);

            FoodUnitResponse[] body = bookNeedResponseResponseEntity.getBody();

            assertEquals(200, bookNeedResponseResponseEntity.getStatusCode().value());
            assertNotNull(body);
            assertEquals(unit1.getId(), body[0].getId());
            assertEquals(unit2.getId(), body[1].getId());
            assertEquals(unit1.getArabicName(), body[0].getArabicName());
            assertEquals(unit2.getArabicName(), body[1].getArabicName());
        } finally {
            dataUtil.delete(unit1, unit2);
        }
    }

    @Test
    void get_withId_thenValidateResponse() {
        FoodUnit unit1 = foodUnitStubs.insert("arabicName", "englishName");

        try {
            ResponseEntity<FoodUnitResponse> bookNeedResponseResponseEntity = requestUtil
                            .get("/api/v1/food/units/%d".formatted(unit1.getId()), null,
                                            null, FoodUnitResponse.class);

            FoodUnitResponse body = bookNeedResponseResponseEntity.getBody();

            assertEquals(200, bookNeedResponseResponseEntity.getStatusCode().value());
            assertNotNull(body);
            assertEquals(unit1.getId(), body.getId());
            assertEquals(unit1.getArabicName(), body.getArabicName());
            assertEquals(unit1.getEnglishName(), body.getEnglishName());
        } finally {
            dataUtil.delete(unit1);
        }
    }
}
