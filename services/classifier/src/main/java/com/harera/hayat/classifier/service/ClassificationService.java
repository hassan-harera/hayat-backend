package com.harera.hayat.classifier.service;

import lombok.extern.slf4j.Slf4j;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

import static com.amazonaws.util.IOUtils.toByteArray;

@Slf4j
@Service
public class ClassificationService {

    private final ComputationGraph model;

    public ClassificationService(ComputationGraph model) {
        this.model = model;
    }

    public void categorize(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        byte[] fileData = toByteArray(inputStream);
        INDArray input = preprocessFileData(fileData);

        INDArray output = model.outputSingle(input);
        log.info("Model output: {}", output);
    }

    private static INDArray preprocessFileData(byte[] fileData) {
        float[] inputData = new float[fileData.length];
        for (int i = 0; i < fileData.length; i++) {
            inputData[i] = fileData[i] & 0xFF;
        }
        return Nd4j.create(inputData, new int[] { 1, inputData.length });
    }
}
