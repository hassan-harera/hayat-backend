package com.harera.hayat.donations.service.ai;

import com.harera.hayat.donations.model.ai.Prediction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PredictionService {

    private final RestTemplate restTemplate;

    public PredictionService() {
        this.restTemplate = new RestTemplate();
    }

    public Prediction predict(String text) {
        return restTemplate.postForObject("http://146.190.206.136:5000/predict", text,
                        Prediction.class);
    }
}
