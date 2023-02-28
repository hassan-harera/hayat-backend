package com.harera.hayat.shared.controller.food;

import com.harera.hayat.framework.model.food.FoodCategory;
import com.harera.hayat.framework.model.food.FoodCategoryResponse;
import com.harera.hayat.shared.ApplicationIT;
import com.harera.hayat.shared.stubs.FoodCategoryStubs;
import com.harera.hayat.shared.util.DataUtil;
import com.harera.hayat.shared.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class FoodCategoryControllerIT extends ApplicationIT {

    private final RequestUtil requestUtil;
    private final FoodCategoryStubs foodCategoryStubs;
    private final DataUtil dataUtil;

    @Test
    void list_withEnglishName_thenValidateResponse() {
        FoodCategory category1 = foodCategoryStubs.insert("arabicName", "englishName");
        FoodCategory category2 = foodCategoryStubs.insert("arabicName", "englishName");

        try {
            ResponseEntity<FoodCategoryResponse[]> bookNeedResponseResponseEntity =
                            requestUtil.get("/api/v1/food/categories", null, null,
                                            FoodCategoryResponse[].class);

            FoodCategoryResponse[] body = bookNeedResponseResponseEntity.getBody();

            assertEquals(200, bookNeedResponseResponseEntity.getStatusCode().value());
            assertNotNull(body);
            assertEquals(category1.getId(), body[0].getId());
            assertEquals(category2.getId(), body[1].getId());
            assertEquals(category1.getArabicName(), body[0].getArabicName());
            assertEquals(category2.getArabicName(), body[1].getArabicName());
        } finally {
            dataUtil.delete(category1, category2);
        }
    }

    @Test
    void get_withId_thenValidateResponse() {
        FoodCategory category1 = foodCategoryStubs.insert("arabicName", "englishName");

        try {
            ResponseEntity<FoodCategoryResponse> bookNeedResponseResponseEntity = requestUtil
                            .get("/api/v1/food/categories/%d".formatted(category1.getId()), null,
                                            null, FoodCategoryResponse.class);

            FoodCategoryResponse body = bookNeedResponseResponseEntity.getBody();

            assertEquals(200, bookNeedResponseResponseEntity.getStatusCode().value());
            assertNotNull(body);
            assertEquals(category1.getId(), body.getId());
            assertEquals(category1.getArabicName(), body.getArabicName());
            assertEquals(category1.getEnglishName(), body.getEnglishName());
        } finally {
            dataUtil.delete(category1);
        }
    }
}
