package com.harera.hayat.notificaions.config;

import com.google.firebase.messaging.FirebaseMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static com.google.auth.oauth2.GoogleCredentials.fromStream;
import static com.google.firebase.FirebaseApp.getApps;
import static com.google.firebase.FirebaseApp.initializeApp;
import static com.google.firebase.FirebaseOptions.builder;
import static java.lang.String.format;

@Configuration
@Slf4j
public class FirebaseConfig {

    private final String type;

    private final String projectId;

    private final String privateKeyId;

    private final String privateKey;

    private final String clientEmail;

    private final String clientId;

    private final String authUri;

    private final String tokenUri;

    private final String authProviderCertUrl;

    private final String clientCertUrl;

    @Autowired
    public FirebaseConfig(@Value("${firebase.type}") String type,
                          @Value("${firebase.project.id}") String projectId,
                          @Value("${firebase.private.key.id}") String privateKeyId,
                          @Value("${firebase.private.key}") String privateKey,
                          @Value("${firebase.client.email}") String clientEmail,
                          @Value("${firebase.client.id}") String clientId,
                          @Value("${firebase.auth.uri}") String authUri,
                          @Value("${firebase.token.uri}") String tokenUri,
                          @Value("${firebase.auth.provider.cert.url}") String authProviderCertUrl,
                          @Value("${firebase.client.cert.url}") String clientCertUrl) {
        this.type = type;
        this.projectId = projectId;
        this.privateKeyId = privateKeyId;
        this.privateKey = privateKey;
        this.clientEmail = clientEmail;
        this.clientId = clientId;
        this.authUri = authUri;
        this.tokenUri = tokenUri;
        this.authProviderCertUrl = authProviderCertUrl;
        this.clientCertUrl = clientCertUrl;
    }

    @PostConstruct
    public void checkFirebaseApps() {
        if (getApps().isEmpty()) {
            initialize();
        }
    }

    private void initialize() {
        try {
            final String config = "{" + "\"type\":\"%s\"," + "\"project_id\":\"%s\","
                            + "\"private_key_id\":\"%s\"," + "\"private_key\":\"%s\","
                            + "\"client_email\": \"%s\"," + "\"client_id\":\"%s\","
                            + "\"auth_uri\": \"%s\"," + "\"token_uri\": \"%s\","
                            + "\"auth_provider_x509_cert_url\": \"%s\","
                            + "\"client_x509_cert_url\": \"%s\"" + "}";
            final String formattedConfig = format(config, type, projectId, privateKeyId,
                            privateKey, clientEmail, clientId, authUri, tokenUri,
                            authProviderCertUrl, clientCertUrl);
            final ByteArrayInputStream credentialsStream =
                            new ByteArrayInputStream(formattedConfig.getBytes());
            initializeApp(builder().setCredentials(fromStream(credentialsStream))
                            .build());
        } catch (IOException e) {
            log.error("Error initializing Firebase", e);
        }
    }

    @Bean
    public FirebaseMessaging firebaseMessaging() {
        return FirebaseMessaging.getInstance();
    }
}
