package com.harera.hayat.classifier.config;

import lombok.extern.log4j.Log4j2;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Log4j2
@Configuration
public class ModelConfig {

    public static final String MODEL_PATH = "model";

    @Bean
    public ComputationGraph computationGraph() {
        try {
            InputStream inputStream = new ClassPathResource("model.h5").getInputStream();
            return KerasModelImport.importKerasModelAndWeights(inputStream);
        } catch (IOException | UnsupportedKerasConfigurationException
                        | InvalidKerasConfigurationException e) {
            log.error(e);
            return null;
        }
    }
}
