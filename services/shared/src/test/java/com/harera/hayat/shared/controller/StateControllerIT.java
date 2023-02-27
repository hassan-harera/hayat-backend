package com.harera.hayat.shared.controller;

import com.harera.hayat.framework.model.city.State;
import com.harera.hayat.framework.model.city.StateResponse;
import com.harera.hayat.shared.ApplicationIT;
import com.harera.hayat.shared.stubs.StateStubs;
import com.harera.hayat.shared.util.DataUtil;
import com.harera.hayat.shared.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class StateControllerIT extends ApplicationIT {

    private final RequestUtil requestUtil;
    private final StateStubs stateStubs;
    private final DataUtil dataUtil;

    @Test
    void list_thenValidateResponse() {
        State state = stateStubs.insert("arabicName", "englishName");

        try {
            ResponseEntity<StateResponse[]> bookNeedResponseResponseEntity = requestUtil
                            .get("/api/v1/states", null, null, StateResponse[].class);

            StateResponse[] body = bookNeedResponseResponseEntity.getBody();

            assertEquals(200, bookNeedResponseResponseEntity.getStatusCode().value());
            assertNotNull(body);
            assertEquals(state.getId(), body[0].getId());
        } finally {
            dataUtil.delete(state);
        }
    }

    @Test
    void get_withId_thenValidateResponse() {
        State state = stateStubs.insert("arabicName", "englishName");

        try {
            ResponseEntity<StateResponse> bookNeedResponseResponseEntity =
                            requestUtil.get("/api/v1/states/%d".formatted(state.getId()),
                                            null, null, StateResponse.class);

            StateResponse body = bookNeedResponseResponseEntity.getBody();

            assertEquals(200, bookNeedResponseResponseEntity.getStatusCode().value());
            assertNotNull(body);
            assertEquals(state.getId(), body.getId());
        } finally {
            dataUtil.delete(state);
        }
    }
}
