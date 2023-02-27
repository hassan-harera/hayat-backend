package com.harera.hayat.shared.util;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.springframework.http.HttpMethod.PUT;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.BasicHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RequestUtil {

    private RestTemplate restTemplate;
    @Value("${server.port}")
    private String serverPort;

    @PostConstruct
    public void setup() {
        try {
            TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                    NoopHostnameVerifier.INSTANCE);

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory> create().register("https", sslsf)
                    .register("http", new PlainConnectionSocketFactory()).build();

            BasicHttpClientConnectionManager connectionManager =
                    new BasicHttpClientConnectionManager(socketFactoryRegistry);
            HttpClient httpClient = HttpClients.custom().disableCookieManagement()
                    .setConnectionManager(connectionManager).build();

            HttpComponentsClientHttpRequestFactory requestFactory =
                    new HttpComponentsClientHttpRequestFactory(httpClient);
            restTemplate = new RestTemplate(requestFactory);
        } catch (KeyStoreException | KeyManagementException
                 | NoSuchAlgorithmException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
    public <T> ResponseEntity<T> post(String path, @Nullable Object body,
                    @Nullable Map<String, Object> headers, Class<T> responseType) {
        String url = String.format("http://localhost:%s/%s", serverPort, path);
        HttpEntity httpEntity = getHttpEntity(body, headers);
        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseType);
    }

    public <T> ResponseEntity<T> get(String path, @Nullable Object body,
                    @Nullable Map<String, Object> headers, Class<T> responseType) {
        String url = String.format("http://localhost:%s/%s", serverPort, path);
        HttpEntity httpEntity = getHttpEntity(body, headers);
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType);
    }

    public <T> ResponseEntity<T> put(String path, @Nullable Object body,
                    @Nullable Map<String, Object> headers, Class<T> responseType) {
        String url = String.format("http://localhost:%s/%s", serverPort, path);
        HttpEntity httpEntity = getHttpEntity(body, headers);
        return restTemplate.exchange(url, PUT, httpEntity, responseType);
    }

    private HttpEntity getHttpEntity(@Nullable Object body, Map<String, Object> headers) {
        HttpHeaders httpHeaders = getHttpHeaders(headers);
        HttpEntity httpEntity;
        if (body != null) {
            httpEntity = new HttpEntity(body, httpHeaders);
        } else {
            httpEntity = new HttpEntity(httpHeaders);
        }
        return httpEntity;
    }

    private HttpHeaders getHttpHeaders(@Nullable Map<String, Object> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        if (isNotEmpty(headers)) {
            headers.forEach((s, o) -> {
                if (o instanceof List) {
                    httpHeaders.put(s, (List<String>) o);
                } else if (o instanceof String) {
                    httpHeaders.add(s, (String) o);
                } else {
                    throw new IllegalArgumentException("this object is not supported");
                }
            });
        }
        return httpHeaders;
    }
}
