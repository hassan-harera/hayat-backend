package com.harera.hayat.framework.config;


import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.harera.hayat.framework.exception.*;
import com.harera.hayat.framework.model.GlobalMessage;
import com.harera.hayat.framework.model.exception.ApiError;
import com.harera.hayat.framework.repository.GlobalMessageRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.log4j.Log4j2;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Log4j2
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private GlobalMessageRepository globalMessageRepository;

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ex);
        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(FieldLimitException.class)
    protected ResponseEntity<Object> handleFieldLimitException(FieldLimitException ex,
                                                               WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        apiError.setCode(ex.getCode());
        apiError.setDisplayMessage(assignDisplayMessage(getLanguage(request),
                ex.getMessage(), ex.getCode()));
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ex);
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return buildResponseEntity(new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                builder.substring(0, builder.length() - 2), ex));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex,
                                                          WebRequest request) {
        log.error(ex);
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        apiError.setCode(ex.getCode());
        apiError.setDisplayMessage(assignDisplayMessage(getLanguage(request),
                ex.getMessage(), ex.getCode()));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(LogicError.class)
    protected ResponseEntity<Object> handleLogicError(LogicError ex, WebRequest request) {
        log.error(ex);
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        apiError.setCode(ex.getCode());
        apiError.setDisplayMessage(assignDisplayMessage(getLanguage(request),
                ex.getMessage(), ex.getCode()));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(DateTimeParseException.class)
    protected ResponseEntity<Object> handleDateTimeParseException(
            DateTimeParseException ex, WebRequest request) {
        log.error(ex);
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        apiError.setDisplayMessage("DATE_TIME_FORMAT");
        return buildResponseEntity(apiError);
    }

    /**
     * Handles UserUniqueException. Created to encapsulate errors with more
     * detail than UserUniqueException
     *
     * @param ex the UniqueFieldException
     * @return the ApiError object
     */
    @ExceptionHandler(UniqueFieldException.class)
    protected ResponseEntity<Object> handleUniqueFieldException(UniqueFieldException ex,
                                                                WebRequest request) {
        log.error(ex);
        ApiError apiError = new ApiError(HttpStatus.CONFLICT);
        apiError.setMessage(ex.getMessage());
        apiError.setCode(ex.getCode());
        apiError.setDisplayMessage(assignDisplayMessage(getLanguage(request),
                ex.getMessage(), ex.getCode()));
        return buildResponseEntity(apiError);
    }

    /**
     * Handles MandatoryFieldException. Created to encapsulate errors with more
     * detail than MandatoryFieldException
     *
     * @param ex the MandatoryFieldException
     * @return the ApiError object
     */
    @ExceptionHandler(MandatoryFieldException.class)
    protected ResponseEntity<Object> handleMandatoryFieldException(
            MandatoryFieldException ex, WebRequest request) {
        log.error(ex);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        apiError.setCode(ex.getCode());
        apiError.setDisplayMessage(assignDisplayMessage(getLanguage(request),
                ex.getMessage(), ex.getCode()));
        return buildResponseEntity(apiError);
    }

    /**
     * Handles FormatException. Created to encapsulate errors with more detail
     * than FormatException
     *
     * @param ex the FieldFormatException
     * @return the ApiError object
     */
    @ExceptionHandler(FieldFormatException.class)
    protected ResponseEntity<Object> handleFieldFormatException(FieldFormatException ex,
                                                                WebRequest request) {
        log.error(ex);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        apiError.setCode(ex.getCode());
        apiError.setDisplayMessage(assignDisplayMessage(getLanguage(request),
                ex.getMessage(), ex.getCode()));
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        log.error("{} to {} with exception {}", servletWebRequest.getHttpMethod(),
                servletWebRequest.getRequest().getServletPath(),
                ex.getLocalizedMessage());
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ex);
        String error = "Error writing JSON output";
        return buildResponseEntity(
                new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ex);
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(String.format("Could not find the [%s] method for URL [%s]",
                ex.getHttpMethod(), ex.getRequestURL()));
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiError object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.error(ex);
        ApiError apiError = new ApiError(BAD_REQUEST);
        final Class<?> requiredType = ex.getRequiredType();
        final String reqSimpleName =
                requiredType == null ? "Unknown" : requiredType.getSimpleName();
        apiError.setMessage(String.format(
                "The parameter [%s] of value [%s] could not be converted to type [%s]",
                ex.getName(), ex.getValue(), reqSimpleName));
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiError object
     */
    @ExceptionHandler({ExpiredJwtException.class, MalformedJwtException.class})
    protected ResponseEntity<Object> handleExpiredJwtException(JwtException ex) {
        log.error(ex);
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED);
        if (ex instanceof ExpiredJwtException) {
            apiError.setMessage("Token is Expired");
        } else if (ex instanceof MalformedJwtException) {
            apiError.setMessage("Token is Malformed");
        }
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({LoginException.class})
    protected ResponseEntity<Object> loginException(LoginException ex) {
        log.error(ex);
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED);
        apiError.setMessage("Invalid Username or Password");
        apiError.setDisplayMessage("Invalid Username or Password");
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({SignupException.class})
    protected ResponseEntity<Object> signupException(SignupException ex) {
        log.error(ex);
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED);
        apiError.setMessage(ex.getMessage());
        apiError.setDisplayMessage(ex.getMessage());
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    private String getLanguage(WebRequest request) {
        final String LANG_HEADER = "language";
        final String header = request.getHeader(LANG_HEADER);
        return StringUtils.isNotEmpty(header) ? header : "en";
    }

    private String assignDisplayMessage(String language, String message, String code) {
        final Optional<GlobalMessage> globalMessage =
                globalMessageRepository.findByLanguageAndCode(language, code);
        String displayMessage;
        if (globalMessage.isPresent()) {
            displayMessage = globalMessage.get().getMessage();
        } else {
            displayMessage = String.format("Code: [%s], Message: [%s]", code, message);
        }
        log.error(displayMessage);
        return displayMessage;
    }

    @ExceptionHandler({HttpServerErrorException.InternalServerError.class})
    public ResponseEntity<Object> handleInternalServerError(
            HttpServerErrorException.InternalServerError ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        log.error(ex);
        String error = "Error writing JSON output";
        return buildResponseEntity(
                new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    @ExceptionHandler({InvalidOtpException.class})
    public ResponseEntity<ApiError> handleInvalidOtpException(
            InvalidOtpException ex, WebRequest request) {
        log.error(ex);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        apiError.setCode(ex.getCode());
        apiError.setDisplayMessage(assignDisplayMessage(getLanguage(request),
                ex.getMessage(), ex.getCode()));
        apiError.setDebugMessage(ex.getMessage());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler({ExpiredOtpException.class})
    public ResponseEntity<ApiError> handleInvalidOtpException(
            ExpiredOtpException ex, WebRequest request) {
        log.error(ex);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        apiError.setCode(ex.getCode());
        apiError.setDisplayMessage(assignDisplayMessage(getLanguage(request),
                ex.getMessage(), ex.getCode()));
        apiError.setDebugMessage(ex.getMessage());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }
}
