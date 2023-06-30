package com.harera.hayat.needs.service;

import com.harera.hayat.needs.model.Prediction;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PredictionService {

    public Prediction predict(String text) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://146.190.206.136:5000/predict";

        String requestBody = "{\"text\":\"%s\"}".formatted(text);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Prediction> responseEntity = restTemplate.exchange(url,
                        HttpMethod.POST, requestEntity, Prediction.class);
        return responseEntity.getBody();
    }
}
