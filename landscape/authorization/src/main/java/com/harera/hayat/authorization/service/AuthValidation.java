package com.harera.hayat.authorization.service;

import static com.harera.hayat.authorization.util.ErrorCode.FORMAT_FIRST_NAME;
import static com.harera.hayat.authorization.util.ErrorCode.FORMAT_LAST_NAME;
import static com.harera.hayat.authorization.util.ErrorCode.FORMAT_LOGIN_SUBJECT;
import static com.harera.hayat.authorization.util.ErrorCode.FORMAT_USER_MOBILE;
import static com.harera.hayat.authorization.util.ErrorCode.INVALID_FIREBASE_TOKEN;
import static com.harera.hayat.authorization.util.ErrorCode.MANDATORY_FIRST_NAME;
import static com.harera.hayat.authorization.util.ErrorCode.MANDATORY_LAST_NAME;
import static com.harera.hayat.authorization.util.ErrorCode.MANDATORY_LOGIN_OAUTH_TOKEN;
import static com.harera.hayat.authorization.util.ErrorCode.MANDATORY_LOGIN_PASSWORD;
import static com.harera.hayat.authorization.util.ErrorCode.MANDATORY_LOGIN_SUBJECT;
import static com.harera.hayat.authorization.util.ErrorCode.MANDATORY_USER_MOBILE;
import static com.harera.hayat.authorization.util.ErrorCode.NOT_FOUND_USERNAME_OR_PASSWORD;
import static com.harera.hayat.authorization.util.ErrorCode.UNIQUE_EMAIL;
import static com.harera.hayat.authorization.util.ErrorCode.UNIQUE_USER_MOBILE;
import static com.harera.hayat.authorization.util.ErrorMessage.INCORRECT_USERNAME_PASSWORD_MESSAGE;
import static com.harera.hayat.authorization.util.StringUtils.isValidEmail;
import static com.harera.hayat.authorization.util.StringUtils.isValidMobile;
import static com.harera.hayat.authorization.util.StringUtils.isValidName;
import static com.harera.hayat.authorization.util.StringUtils.isValidPassword;
import static com.harera.hayat.authorization.util.SubjectUtils.getSubject;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.firebase.auth.FirebaseToken;
import com.harera.hayat.authorization.exception.FieldFormatException;
import com.harera.hayat.authorization.exception.LoginException;
import com.harera.hayat.authorization.exception.MandatoryFieldException;
import com.harera.hayat.authorization.exception.UniqueFieldException;
import com.harera.hayat.authorization.model.user.User;
import com.harera.hayat.authorization.model.auth.LoginRequest;
import com.harera.hayat.authorization.model.auth.OAuthLoginRequest;
import com.harera.hayat.authorization.model.auth.SignupRequest;
import com.harera.hayat.authorization.repository.UserRepository;
import com.harera.hayat.authorization.service.firebase.FirebaseServiceImpl;
import com.harera.hayat.authorization.util.ErrorCode;
import com.harera.hayat.authorization.util.Subject;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AuthValidation {

    private final FirebaseServiceImpl firebaseServiceImpl;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthValidation(FirebaseServiceImpl firebaseService,
                    UserRepository userRepository, PasswordEncoder encoder) {
        this.firebaseServiceImpl = firebaseService;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public void validate(SignupRequest signupRequest) {
        validateMandatory(signupRequest);
        validateFormat(signupRequest);
        validateExisting(signupRequest);
    }

    public void validateLogin(LoginRequest loginRequest) {
        validateMandatory(loginRequest);
        validateFormat(loginRequest);
        validateExisting(loginRequest);
        validatePassword(loginRequest);
    }

    private void validateFormat(LoginRequest loginRequest) {
        FieldFormatException incorrectUsernameFormat = new FieldFormatException(
                        FORMAT_LOGIN_SUBJECT, "subject", loginRequest.getSubject());

        String subjectPayload = loginRequest.getSubject();
        Subject subjectType = getSubject(subjectPayload);

        if (!(subjectType instanceof Subject.Email)
                        && !(subjectType instanceof Subject.PhoneNumber)) {
            throw incorrectUsernameFormat;
        }
    }

    public void validate(OAuthLoginRequest loginRequest) {
        validateMandatory(loginRequest);
        FirebaseToken firebaseToken = firebaseServiceImpl
                        .getFirebaseToken(loginRequest.getDeviceToken());
        validateUidExisted(firebaseToken.getUid());
    }

    private void validateUidExisted(String uid) {
        if (!userRepository.existsByUid(uid)) {
            throw new LoginException(INVALID_FIREBASE_TOKEN, "Invalid firebase token");
        }
    }

    private void validateFormat(SignupRequest signupRequest) {
        //format validation: firstName (3, 24), lastName (3, 24), password (6, 68), email (email pattern)
        if (!isValidMobile(signupRequest.getMobile())) {
            throw new FieldFormatException(FORMAT_USER_MOBILE, "Incorrect mobile format");
        }
        if (!isValidName(signupRequest.getFirstName())) {
            throw new FieldFormatException(FORMAT_FIRST_NAME,
                            "Incorrect first name format");
        }
        if (!isValidName(signupRequest.getLastName())) {
            throw new FieldFormatException(FORMAT_LAST_NAME, MANDATORY_LAST_NAME);
        }
        if (!isValidPassword(signupRequest.getPassword())) {
            throw new FieldFormatException(ErrorCode.FORMAT_SIGNUP_PASSWORD,
                            MANDATORY_LAST_NAME);
        }
        if (signupRequest.getEmail() != null && !isValidEmail(signupRequest.getEmail())) {
            throw new FieldFormatException(ErrorCode.FORMAT_SIGNUP_EMAIL,
                            MANDATORY_LAST_NAME);
        }
    }

    private void validateExisting(LoginRequest loginRequest) {
        validateSubjectExisted(loginRequest.getSubject());
    }

    private void validateSubjectExisted(String subject) {
        Subject subjectType = getSubject(subject);
        if (subjectType instanceof Subject.Email) {
            validateEmailExisted(subject);
        } else if (subjectType instanceof Subject.PhoneNumber) {
            validatePhoneNumberExisted(subject);
        } else {
            validateUsernameExisted(subject);
        }
    }

    private void validatePhoneNumberExisted(String subject) {
        if (!userRepository.existsByMobile(subject)) {
            throw new LoginException(NOT_FOUND_USERNAME_OR_PASSWORD,
                            INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateUsernameExisted(String subject) {
        if (!userRepository.existsByUsername(subject)) {
            throw new LoginException(NOT_FOUND_USERNAME_OR_PASSWORD,
                            INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateExisting(SignupRequest signupRequest) {
        validateMobileNotExisted(signupRequest.getMobile());
        if (signupRequest.getEmail() != null) {
            validateEmailNotExisted(signupRequest.getEmail());
        }
    }

    private void validateMobileNotExisted(String phoneNumber) {
        if (userRepository.existsByMobile(phoneNumber)) {
            throw new UniqueFieldException(UNIQUE_USER_MOBILE, "subject", phoneNumber);
        }
    }

    private void validateEmailNotExisted(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UniqueFieldException(UNIQUE_EMAIL, "subject", email);
        }
    }

    private void validateMandatory(SignupRequest signupRequest) {
        //mandatory validation: mobile, firstName, lastName, password
        if (!StringUtils.hasText(signupRequest.getMobile())) {
            throw new MandatoryFieldException(MANDATORY_USER_MOBILE, "mobile");
        }
        if (!StringUtils.hasText(signupRequest.getFirstName())) {
            throw new MandatoryFieldException(MANDATORY_FIRST_NAME, "firstName");
        }
        if (!StringUtils.hasText(signupRequest.getLastName())) {
            throw new MandatoryFieldException(MANDATORY_LAST_NAME, "lastName");
        }
        if (!StringUtils.hasText(signupRequest.getPassword())) {
            throw new MandatoryFieldException(MANDATORY_LOGIN_PASSWORD, "password");
        }
    }

    private void validatePassword(LoginRequest loginRequest) {
        String subjectPayload = loginRequest.getSubject();
        Subject subjectType = getSubject(subjectPayload);
        Optional<User> user;
        if (subjectType instanceof Subject.PhoneNumber) {
            user = userRepository.findByMobile(subjectPayload);
        } else if (subjectType instanceof Subject.Email) {
            user = userRepository.findByEmail(subjectPayload);
        } else {
            user = userRepository.findByUsername(subjectPayload);
        }
        user.ifPresent(u -> validatePassword(loginRequest.getPassword(),
                        u.getPassword()));
    }

    private void validateMandatory(OAuthLoginRequest loginRequest) {
        if (isBlank(loginRequest.getFirebaseToken())) {
            throw new MandatoryFieldException(MANDATORY_LOGIN_OAUTH_TOKEN, "oauth_token");
        }
    }

    private void validatePassword(String password, String encodedPassword) {
        if (!encoder.matches(password, encodedPassword)) {
            throw new LoginException(NOT_FOUND_USERNAME_OR_PASSWORD,
                            INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateEmailExisted(String subject) {
        if (!userRepository.existsByEmail(subject)) {
            throw new LoginException(NOT_FOUND_USERNAME_OR_PASSWORD,
                            INCORRECT_USERNAME_PASSWORD_MESSAGE);
        }
    }

    private void validateMandatory(LoginRequest loginRequest) {
        if (!StringUtils.hasText(loginRequest.getSubject())) {
            throw new MandatoryFieldException(MANDATORY_LOGIN_SUBJECT, "subject");
        }

        if (!StringUtils.hasText(loginRequest.getPassword())) {
            throw new MandatoryFieldException(MANDATORY_LOGIN_PASSWORD, "password");
        }
    }
}
