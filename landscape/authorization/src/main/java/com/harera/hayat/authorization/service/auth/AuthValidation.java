package com.harera.hayat.authorization.service.auth;

import com.harera.hayat.authorization.model.auth.LoginRequest;
import com.harera.hayat.authorization.model.auth.ResetPasswordRequest;
import com.harera.hayat.authorization.model.auth.SignupRequest;
import com.harera.hayat.authorization.model.otp.OTP;
import com.harera.hayat.authorization.model.user.User;
import com.harera.hayat.authorization.repository.UserRepository;
import com.harera.hayat.authorization.repository.otp.OtpRepository;
import com.harera.hayat.framework.exception.*;
import com.harera.hayat.framework.util.ErrorCode;
import com.harera.hayat.framework.util.Subject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.harera.hayat.authorization.util.StringUtils.*;
import static com.harera.hayat.framework.util.ErrorCode.*;
import static com.harera.hayat.framework.util.ErrorMessage.INCORRECT_USERNAME_PASSWORD_MESSAGE;
import static com.harera.hayat.framework.util.SubjectUtils.getSubject;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

@Log4j2
@Service
public class AuthValidation {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final OtpRepository otpRepository;

    @Autowired
    public AuthValidation(UserRepository userRepository, PasswordEncoder encoder,
                    OtpRepository otpRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.otpRepository = otpRepository;
    }

    public void validateLogin(LoginRequest loginRequest) {
        validateMandatory(loginRequest);
        validateFormat(loginRequest);
        validateExisting(loginRequest);
        validatePassword(loginRequest);
    }

    public void validateSignup(SignupRequest signupRequest) {
        validateMandatory(signupRequest);
        validateFormat(signupRequest);
        validateExisting(signupRequest);
        validateOtp(signupRequest);
    }

    private void validateOtp(SignupRequest signupRequest) {
        OTP otp = otpRepository.findById(signupRequest.getMobile())
                        .orElseThrow(() -> new ExpiredOtpException(
                                        signupRequest.getMobile(),
                                        signupRequest.getOtp()));
        if (!otp.getOtp().equals(signupRequest.getOtp())) {
            throw new InvalidOtpException(signupRequest.getMobile(),
                            signupRequest.getOtp());
        }
    }

    private void validateFormat(LoginRequest loginRequest) {
        Subject subjectType = getSubject(loginRequest.getSubject());

        if (!(subjectType instanceof Subject.Email)
                        && !(subjectType instanceof Subject.PhoneNumber)) {
            throw new FieldFormatException(FORMAT_LOGIN_SUBJECT, "subject",
                            loginRequest.getSubject());
        }
    }

    private void validateFormat(SignupRequest signupRequest) {
        if (!isValidMobile(signupRequest.getMobile())) {
            throw new FieldFormatException(FORMAT_SIGNUP_MOBILE, "mobile",
                            signupRequest.getMobile());
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
            throw new FieldFormatException(FORMAT_SIGNUP_PASSWORD, "password",
                            signupRequest.getPassword());
        }
        if (isNotEmpty(signupRequest.getEmail())
                        && !isValidEmail(signupRequest.getEmail())) {
            throw new FieldFormatException(FORMAT_SIGNUP_EMAIL, "email",
                            signupRequest.getEmail());
        }
        if (!isValidOtp(signupRequest.getOtp())) {
            throw new FieldFormatException(ErrorCode.FORMAT_SIGNUP_OTP, "otp",
                            signupRequest.getOtp());
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
        if (isEmpty(signupRequest.getMobile())) {
            throw new MandatoryFieldException(MANDATORY_SIGNUP_MOBILE, "mobile");
        }
        if (isEmpty(signupRequest.getFirstName())) {
            throw new MandatoryFieldException(MANDATORY_SIGNUP_FIRST_NAME, "first_name");
        }
        if (isEmpty(signupRequest.getLastName())) {
            throw new MandatoryFieldException(MANDATORY_SIGNUP_LAST_NAME, "last_name");
        }
        if (isEmpty(signupRequest.getPassword())) {
            throw new MandatoryFieldException(MANDATORY_SIGNUP_PASSWORD, "password");
        }
        if (isEmpty(signupRequest.getOtp())) {
            throw new MandatoryFieldException(MANDATORY_SIGNUP_OTP, "otp");
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
        if (isEmpty(loginRequest.getSubject())) {
            throw new MandatoryFieldException(MANDATORY_LOGIN_SUBJECT, "subject");
        }

        if (isEmpty(loginRequest.getPassword())) {
            throw new MandatoryFieldException(MANDATORY_LOGIN_PASSWORD, "password");
        }
    }

    public void validateResetPassword(ResetPasswordRequest resetPasswordRequest) {
        validateMandatory(resetPasswordRequest);
        validateFormat(resetPasswordRequest);
        validateExisting(resetPasswordRequest);
        validateOtp(resetPasswordRequest);
    }

    private void validateOtp(ResetPasswordRequest resetPasswordRequest) {
        OTP otp = otpRepository.findById(resetPasswordRequest.getMobile())
                        .orElseThrow(() -> new ExpiredOtpException(
                                        resetPasswordRequest.getMobile(),
                                        resetPasswordRequest.getOtp()));
        if (!otp.getOtp().equals(resetPasswordRequest.getOtp())) {
            throw new InvalidOtpException(resetPasswordRequest.getMobile(),
                            resetPasswordRequest.getOtp());
        }
    }

    private void validateExisting(ResetPasswordRequest resetPasswordRequest) {
        if (!userRepository.existsByMobile(resetPasswordRequest.getMobile())) {
            throw new FieldFormatException(NOT_FOUND_RESET_PASSWORD_MOBILE,
                            "Mobile not existed");
        }
    }

    private void validateFormat(ResetPasswordRequest resetPasswordRequest) {
        if (!isValidMobile(resetPasswordRequest.getMobile())) {
            throw new FieldFormatException(FORMAT_RESET_PASSWORD_MOBILE, "mobile",
                            resetPasswordRequest.getMobile());
        }
        if (!isValidPassword(resetPasswordRequest.getNewPassword())) {
            throw new FieldFormatException(FORMAT_RESET_PASSWORD_NEW_PASSWORD,
                            "new_password", resetPasswordRequest.getNewPassword());
        }
        if (!isValidOtp(resetPasswordRequest.getOtp())) {
            throw new FieldFormatException(FORMAT_RESET_PASSWORD_OTP, "otp",
                            resetPasswordRequest.getOtp());
        }
    }

    private boolean isValidOtp(String otp) {
        return otp != null && otp.matches("^[0-9]{6}$");
    }

    private void validateMandatory(ResetPasswordRequest resetPasswordRequest) {
        if (isEmpty(resetPasswordRequest.getMobile())) {
            throw new MandatoryFieldException(MANDATORY_RESET_PASSWORD_MOBILE, "mobile");
        }
        if (isEmpty(resetPasswordRequest.getOtp())) {
            throw new MandatoryFieldException(MANDATORY_RESET_PASSWORD_OTP, "otp");
        }
        if (isEmpty(resetPasswordRequest.getNewPassword())) {
            throw new MandatoryFieldException(MANDATORY_RESET_PASSWORD_NEW_PASSWORD,
                            "password");
        }
    }
}
