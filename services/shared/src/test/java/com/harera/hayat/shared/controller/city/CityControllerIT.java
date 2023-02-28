package com.harera.hayat.shared.controller.city;

import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.city.CityResponse;
import com.harera.hayat.shared.ApplicationIT;
import com.harera.hayat.shared.stubs.CityStubs;
import com.harera.hayat.shared.util.DataUtil;
import com.harera.hayat.shared.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class CityControllerIT extends ApplicationIT {

    private final RequestUtil requestUtil;
    private final CityStubs cityStubs;
    private final DataUtil dataUtil;

    @Test
    void search_withEnglishName_thenValidateResponse() {
        City city = cityStubs.insert("arabicName", "englishName");

        String name = "eng";
        try {
            ResponseEntity<CityResponse[]> bookNeedResponseResponseEntity =
                            requestUtil.get("/api/v1/cities/search?=%s".formatted(name),
                                            null, null, CityResponse[].class);

            CityResponse[] body = bookNeedResponseResponseEntity.getBody();

            assertEquals(200, bookNeedResponseResponseEntity.getStatusCode().value());
            assertNotNull(body);
            assertEquals(city.getId(), body[0].getId());
        } finally {
            dataUtil.delete(city);
        }
    }

    @Test
    void search_withArabicName_thenValidateResponse() {
        City city = cityStubs.insert("arabicName", "englishName");

        String name = "ara";
        try {
            ResponseEntity<CityResponse[]> bookNeedResponseResponseEntity =
                            requestUtil.get("/api/v1/cities/search?=%s".formatted(name),
                                            null, null, CityResponse[].class);

            CityResponse[] body = bookNeedResponseResponseEntity.getBody();

            assertEquals(200, bookNeedResponseResponseEntity.getStatusCode().value());
            assertNotNull(body);
            assertEquals(city.getId(), body[0].getId());
        } finally {
            dataUtil.delete(city);
        }
    }

    @Test
    void get_withId_thenValidateResponse() {
        City city = cityStubs.insert("arabicName", "englishName");

        try {
            ResponseEntity<CityResponse> bookNeedResponseResponseEntity =
                            requestUtil.get("/api/v1/cities/%d".formatted(city.getId()),
                                            null, null, CityResponse.class);

            CityResponse body = bookNeedResponseResponseEntity.getBody();

            assertEquals(200, bookNeedResponseResponseEntity.getStatusCode().value());
            assertNotNull(body);
            assertEquals(city.getId(), body.getId());
        } finally {
            dataUtil.delete(city);
        }
    }
}
