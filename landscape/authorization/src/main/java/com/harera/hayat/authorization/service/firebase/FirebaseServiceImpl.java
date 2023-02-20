package com.harera.hayat.authorization.service.firebase;

import com.harera.hayat.framework.exception.InvalidTokenException;
import com.harera.hayat.framework.exception.LoginException;
import com.harera.hayat.framework.util.ErrorCode;
import kotlin.jvm.internal.Intrinsics;
import lombok.extern.log4j.Log4j2;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.harera.hayat.authorization.model.oauth.OauthSignupRequest;
import com.harera.hayat.authorization.model.user.FirebaseUser;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Log4j2
@Service
public class FirebaseServiceImpl {

    private final FirebaseAuth firebaseAuth;
    private final ModelMapper modelMapper;

    protected FirebaseServiceImpl(FirebaseAuth firebaseAuth, ModelMapper modelMapper) {
        this.firebaseAuth = firebaseAuth;
        this.modelMapper = modelMapper;
    }

    public FirebaseToken getFirebaseToken(String token) {
        if (isBlank(token))
            throw new InvalidTokenException(ErrorCode.INVALID_FIREBASE_TOKEN,
                    "Invalid Token");
        try {
            FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(token, true);
            if (firebaseToken == null)
                throw new InvalidTokenException(ErrorCode.INVALID_FIREBASE_TOKEN,
                        "Invalid Token");
            return firebaseToken;
        } catch (FirebaseAuthException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    public UserRecord getUser(String uid) {
        if (isBlank(uid))
            throw new InvalidTokenException(ErrorCode.INVALID_FIREBASE_UID,
                    "Invalid uid");
        try {
            UserRecord user = firebaseAuth.getUser(uid);
            if (user == null)
                throw new InvalidTokenException(ErrorCode.INVALID_FIREBASE_TOKEN,
                        "Invalid Token");
            return user;
        } catch (FirebaseAuthException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public FirebaseUser createUser(@NotNull OauthSignupRequest oauthSignupRequest) {
        Intrinsics.checkNotNullParameter(oauthSignupRequest, "signupRequest");
        UserRecord.CreateRequest userRecord = (new UserRecord.CreateRequest())
                .setPhoneNumber("+2" + oauthSignupRequest.getMobile())
                .setPassword(oauthSignupRequest.getPassword())
                .setDisplayName(oauthSignupRequest.getFirstName() + " "
                        + oauthSignupRequest.getLastName())
                .setPassword(oauthSignupRequest.getPassword()).setDisabled(false);

        if (oauthSignupRequest.getEmail() != null) {
            userRecord.setEmail(oauthSignupRequest.getEmail());
        }

        try {
            return modelMapper.map(firebaseAuth.createUser(userRecord),
                    FirebaseUser.class);
        } catch (FirebaseAuthException e) {
            throw new LoginException(ErrorCode.INVALID_FIREBASE_TOKEN, "Invalid token");
        }
    }

    public String generateToken(String uid) {
        if (isBlank(uid))
            throw new InvalidTokenException(ErrorCode.INVALID_FIREBASE_UID,
                    "Invalid uid");
        try {
            return firebaseAuth.createCustomToken(uid);
        } catch (FirebaseAuthException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }
}
