package com.harera.hayat.authorization.util;

import static org.apache.commons.collections4.MapUtils.isNotEmpty;
import static org.springframework.http.HttpMethod.PUT;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.harera.hayat.authorization.model.user.Role;
import com.harera.hayat.authorization.model.auth.LoginRequest;
import com.harera.hayat.authorization.model.auth.LoginResponse;
import com.harera.hayat.authorization.service.AuthService;
import com.harera.hayat.authorization.service.JwtService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RequestUtil {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthService authService;
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
            CloseableHttpClient httpClient = HttpClients.custom()
                            .setSSLSocketFactory(sslsf).disableCookieManagement()
                            .setConnectionManager(connectionManager).build();

            HttpComponentsClientHttpRequestFactory requestFactory =
                            new HttpComponentsClientHttpRequestFactory(httpClient);
            restTemplate = new RestTemplate(requestFactory);
        } catch (KeyStoreException | KeyManagementException
                        | NoSuchAlgorithmException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public <T> ResponseEntity<T> postWithAuth(String path, @Nullable Object body,
                    @Nullable Map<String, Object> headers, Class<T> responseType) {
        return postWithAuth(Role.SYS_ADMIN, path, body, headers, responseType);
    }

    public <T> ResponseEntity<T> postWithAuth(Role userType, String path,
                    @Nullable Object body, @Nullable Map<String, Object> headers,
                    Class<T> responseType) {
        final LoginResponse authResponse = getAuthResponse(userType);
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put("Authorization", "Bearer " + authResponse.getToken());
        return post(path, body, headers, responseType);
    }

    public <T> ResponseEntity<T> post(String path, @Nullable Object body,
                    @Nullable Map<String, Object> headers, Class<T> responseType) {
        String url = String.format("http://localhost:%s/%s", serverPort, path);
        HttpEntity httpEntity = getHttpEntity(body, headers);
        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseType);
    }

    public <T> ResponseEntity<T> getWithAuth(String path, @Nullable Object body,
                    @Nullable Map<String, Object> headers, Class<T> responseType) {
        return getWithAuth(Role.SYS_ADMIN, path, body, headers, responseType);
    }

    public <T> ResponseEntity<T> getWithAuth(Role userType, String path,
                    @Nullable Object body, @Nullable Map<String, Object> headers,
                    Class<T> responseType) {
        final LoginResponse authResponse = getAuthResponse(userType);
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put("Authorization", "Bearer " + authResponse.getToken());
        return get(path, body, headers, responseType);
    }

    public <T> ResponseEntity<T> get(String path, @Nullable Object body,
                    @Nullable Map<String, Object> headers, Class<T> responseType) {
        String url = String.format("http://localhost:%s/%s", serverPort, path);
        HttpEntity httpEntity = getHttpEntity(body, headers);
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType);
    }

    public <T> ResponseEntity<T> putWithAuth(String path, @Nullable Object body,
                    @Nullable Map<String, Object> headers, Class<T> responseType) {
        return putWithAuth(Role.SYS_ADMIN, path, body, headers, responseType);
    }

    public <T> ResponseEntity<T> putWithAuth(Role userType, String path,
                    @Nullable Object body, @Nullable Map<String, Object> headers,
                    Class<T> responseType) {
        final LoginResponse authResponse = getAuthResponse(userType);
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put("Authorization", "Bearer " + authResponse.getToken());
        return put(path, body, headers, responseType);
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

    private LoginResponse getAuthResponse(Role roleType) {
        LoginRequest authCredentialsRequest = new LoginRequest();
        authCredentialsRequest.setSubject("01000000001");
        authCredentialsRequest.setPassword("admin");
        LoginResponse loginResponse = authService.login(authCredentialsRequest);
        return loginResponse;
    }
}
