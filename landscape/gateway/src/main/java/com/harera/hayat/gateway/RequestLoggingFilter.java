package com.harera.hayat.gateway;

import java.util.LinkedList;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

    private static final String[] HEADERS = { "user-agent", "x-forwarded-for" };

    public RequestLoggingFilter() {
        setIncludeHeaders(true);
        setIncludeQueryString(true);
        setIncludePayload(true);
        setMaxPayloadLength(1000);
        setIncludeClientInfo(true);
    }

    @Override
    protected void beforeRequest(final HttpServletRequest httpServletRequest,
                    final String message) {
        if (isLoggable(httpServletRequest))
            log.debug(message);
    }

    @Override
    protected void afterRequest(final HttpServletRequest httpServletRequest,
                    final String message) {
        if (isLoggable(httpServletRequest))
            log.debug(message);
    }

    @Override
    protected String createMessage(HttpServletRequest request, String prefix,
                    String suffix) {
        StringBuilder msg = new StringBuilder();
        msg.append(prefix);
        msg.append(request.getMethod()).append(" ");
        msg.append(request.getRequestURI());
        includeOptionalRequestInfo(request, msg);
        msg.append(suffix);
        return msg.toString();
    }

    private boolean isLoggable(HttpServletRequest httpServletRequest) {
        final String requestURI = httpServletRequest.getRequestURI();
        return !(requestURI.startsWith("/swagger-ui")
                        || requestURI.startsWith("/swagger-resources")
                        || requestURI.startsWith("/v2/api-docs"));
    }

    private void includeOptionalRequestInfo(HttpServletRequest request,
                    StringBuilder msg) {
        includeQueryString(request, msg);
        includeClientInfo(request, msg);
        includeHeader(request, msg);
        includePayload(request, msg);
    }

    private void includePayload(HttpServletRequest request, StringBuilder msg) {
        if (isIncludePayload()
                        && !request.getServletPath().equals("/token/authenticate")) {
            String payload = getMessagePayload(request);
            if (payload != null) {
                msg.append(", payload=").append(payload);
            }
        }
    }

    private void includeHeader(HttpServletRequest request, StringBuilder msg) {

        if (isIncludeHeaders()) {
            HttpHeaders headers = new ServletServerHttpRequest(request).getHeaders();
            StringBuilder includedHeaders = new StringBuilder("[");
            for (int i = 0; i < HEADERS.length; i++) {
                String headerName = HEADERS[i];
                final String headerValue = headers
                                .getOrDefault(headerName, new LinkedList<>()).toString()
                                .replace("[", "").replace("]", "");
                includedHeaders.append(headerName).append(":\"").append(headerValue)
                                .append("\"");
                if (i != HEADERS.length - 1) {
                    includedHeaders.append(", ");
                }
            }
            includedHeaders.append("]");
            msg.append(", headers=").append(includedHeaders);
        }
    }

    private void includeClientInfo(HttpServletRequest request, StringBuilder msg) {
        if (isIncludeClientInfo()) {
            String client = request.getRemoteAddr();
            if (StringUtils.hasLength(client)) {
                msg.append(", client=").append(client);
            }
            if (request.getSession(false) != null) {
                Object user = request.getSession(false).getAttribute("loggedUser");
                if (user != null) {
                    msg.append(", user=").append(user);
                }
            }
        }
    }

    private void includeQueryString(HttpServletRequest request, StringBuilder msg) {
        String queryString = request.getQueryString();
        if (isIncludeQueryString() && queryString != null) {
            msg.append('?').append(queryString);
        }
    }
}
