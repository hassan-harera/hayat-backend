package com.harera.hayat.authorization.service.oauth;

import com.google.firebase.auth.FirebaseToken;
import com.harera.hayat.authorization.model.oauth.OauthLoginRequest;
import com.harera.hayat.authorization.model.oauth.OauthSignupRequest;
import com.harera.hayat.authorization.repository.UserRepository;
import com.harera.hayat.authorization.service.firebase.OauthFirebaseService;
import com.harera.hayat.framework.exception.*;
import com.harera.hayat.framework.util.ErrorCode;
import org.springframework.stereotype.Service;

import static com.harera.hayat.authorization.util.StringUtils.*;
import static com.harera.hayat.framework.util.ErrorCode.*;
import static org.apache.commons.lang.StringUtils.*;

@Service
public class OauthValidation {

    private final OauthFirebaseService oauthFirebaseService;
    private final UserRepository userRepository;

    public OauthValidation(OauthFirebaseService firebaseService,
                    UserRepository userRepository) {
        this.oauthFirebaseService = firebaseService;
        this.userRepository = userRepository;
    }

    public void validateLogin(OauthLoginRequest loginRequest) {
        validateMandatory(loginRequest);
        validateOauthToken(loginRequest);
    }

    public void validateSignup(OauthSignupRequest signupRequest) {
        validateMandatory(signupRequest);
        validateFormat(signupRequest);
        validateExisting(signupRequest);
        validateOauthToken(signupRequest);
    }

    private void validateOauthToken(OauthLoginRequest loginRequest) {
        FirebaseToken firebaseToken = oauthFirebaseService
                        .getFirebaseToken(loginRequest.getFirebaseToken());
        if (userRepository.findByUid(firebaseToken.getUid()).isEmpty()) {
            throw new LoginException(INVALID_FIREBASE_TOKEN, "Invalid firebase token");
        }
    }

    private void validateOauthToken(OauthSignupRequest signupRequest) {
        FirebaseToken firebaseToken = oauthFirebaseService
                        .getFirebaseToken(signupRequest.getOauthToken());
        if (userRepository.findByUid(firebaseToken.getUid()).isPresent()) {
            throw new SignupException("user already exists");
        }
    }

    private void validateFormat(OauthSignupRequest signupRequest) {
        if (!isValidMobile(signupRequest.getMobile())) {
            throw new FieldFormatException(FORMAT_SIGNUP_MOBILE,
                            "Incorrect mobile format");
        }
        if (!isValidName(signupRequest.getFirstName())) {
            throw new FieldFormatException(FORMAT_SIGNUP_FIRST_NAME, "first_name",
                            signupRequest.getFirstName());
        }
        if (!isValidName(signupRequest.getLastName())) {
            throw new FieldFormatException(FORMAT_SIGNUP_LAST_NAME, "last_name",
                            signupRequest.getLastName());
        }
        if (!isValidPassword(signupRequest.getPassword())) {
            throw new FieldFormatException(ErrorCode.FORMAT_SIGNUP_PASSWORD, "password",
                            signupRequest.getPassword());
        }
        if (isNotBlank(signupRequest.getEmail())
                        && !isValidEmail(signupRequest.getEmail())) {
            throw new FieldFormatException(ErrorCode.FORMAT_SIGNUP_EMAIL,
                            MANDATORY_LAST_NAME);
        }
    }

    private void validateExisting(OauthSignupRequest signupRequest) {
        if (userRepository.existsByMobile(signupRequest.getMobile())) {
            throw new UniqueFieldException(UNIQUE_SIGNUP_MOBILE, "mobile",
                            signupRequest.getMobile());
        }

        if (isNotEmpty(signupRequest.getEmail())
                        && userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new UniqueFieldException(UNIQUE_SIGNUP_EMAIL, "email",
                            signupRequest.getEmail());
        }
    }

    private void validateMandatory(OauthSignupRequest signupRequest) {
        if (isEmpty(signupRequest.getOauthToken())) {
            throw new MandatoryFieldException(MANDATORY_SIGNUP_OAUTH_TOKEN,
                            "oauth_token");
        }
        if (isEmpty(signupRequest.getMobile())) {
            throw new MandatoryFieldException(MANDATORY_SIGNUP_MOBILE, "mobile");
        }
        if (isEmpty(signupRequest.getPassword())) {
            throw new MandatoryFieldException(MANDATORY_SIGNUP_PASSWORD, "password");
        }
        if (isEmpty(signupRequest.getFirstName())) {
            throw new MandatoryFieldException(MANDATORY_SIGNUP_FIRST_NAME, "first_name");
        }
        if (isEmpty(signupRequest.getLastName())) {
            throw new MandatoryFieldException(MANDATORY_SIGNUP_LAST_NAME, "last_name");
        }
    }

    private void validateMandatory(OauthLoginRequest loginRequest) {
        if (isBlank(loginRequest.getFirebaseToken())) {
            throw new MandatoryFieldException(MANDATORY_LOGIN_OAUTH_TOKEN, "oauth_token");
        }
    }
}
