package com.harera.hayat.authorization.util;

public final class ErrorCode {

    public static final String MANDATORY_SUBJECT = "subject_001";

    public static final String MANDATORY_UID = "uid_001";

    public static final String MANDATORY_FIRST_NAME = "first_name_001";
    public static final String FORMAT_FIRST_NAME = "first_name_002";

    public static final String MANDATORY_LAST_NAME = "last_name_001";
    public static final String FORMAT_LAST_NAME = "last_name_002";

    public static final String MANDATORY_LOGIN_OAUTH_TOKEN = "token_001";

    public static final String MANDATORY_LOGIN_SUBJECT = "username_001";

    public static final String UNIQUE_USER_NAME = "username_002";

    public static final String MANDATORY_LOGIN_PASSWORD = "password_001";

    public static final String NOT_FOUND_USERNAME_OR_PASSWORD = "login_001";

    public static final String INVALID_FIREBASE_TOKEN = "signup_001";
    public static final String INCORRECT_USERNAME_FORMAT = "login_002";
    public static final String UNIQUE_EMAIL = "email_001";

    /**
     * Mobile
     */
    public static final String UNIQUE_USER_MOBILE = "mobile_001";
    public static final String FORMAT_USER_MOBILE = "mobile_002";
    public static final String MANDATORY_USER_MOBILE = "mobile_003";

    public static final String FORMAT_USER_NAME_MINIMUM = "username_002";
    public static final String FORMAT_USER_NAME_LENGTH = "username_003";
    public static final String FORMAT_USER_NAME_INVALID_CHARS = "username_004";
    public static final String FORMAT_USER_PASSWORD = "password_002";
    public static final String NOT_FOUND_CITY_ID = "city_001";
    public static final String NOT_FOUND_STATE_ID = "state_001";
    public static final String MANDATORY_MEDICINE_UNIT_ID = "medicine_unit_001";
    public static final String MANDATORY_CITY_ID = "city_002";
    public static final String FORMAT_EXPIRATION_DATE = "expiration_date_002";
    public static final String FORMAT_UNIT = "unit_001";
    public static final String MANDATORY_COMMUNICATION = "communication_001";
    public static final String FORMAT_SIGNUP_EMAIL = "signup_002";
    public static final String FORMAT_SIGNUP_PASSWORD = "signup_003";

    public static final String NOT_FOUND_DONATION_UNIT = "donation_001";
    public static final String NOT_FOUND_DONATION_CITY = "donation_002";
    public static final String NOT_FOUND_DONATION_MEDICINE = "donation_003";

    public static final String MANDATORY_DONATION_COMMUNICATION_METHOD = "donation_004";
    public static final String MANDATORY_DONATION_EXPIRATION_DATE = "donation_005";
    public static final String MANDATORY_DONATION_TITLE = "donation_006";
    public static final String MANDATORY_DONATION_AMOUNT = "donation_007";
    public static final String MANDATORY_DONATION_DATE = "donation_011";

    public static final String FORMAT_DONATION_TITLE = "donation_008";
    public static final String FORMAT_DONATION_EXPIRATION_DATE = "donation_009";
    public static final String FORMAT_DONATION_AMOUNT = "donation_010";

    public static final String MANDATORY_MEDICINE_DONATION_AMOUNT =
                    "medicine_donation_001";
    public static final String MANDATORY_MEDICINE_DONATION_MEDICINE_EXPIRATION_DATE =
                    "medicine_donation_002";
    public static final String MANDATORY_MEDICINE_DONATION_MEDICINE =
                    "medicine_donation_003";

    public static final String MANDATORY_FOOD_DONATION_AMOUNT = "food_donation_001";
    public static final String MANDATORY_FOOD_DONATION_FOOD_EXPIRATION_DATE =
                    "food_donation_002";
    public static final String MANDATORY_FOOD_DONATION_UNIT = "food_donation_003";

    public static final String FORMAT_FOOD_DONATION_AMOUNT = "food_donation_004";
    public static final String FORMAT_LOGIN_SUBJECT = "login_003";
    public static final String INVALID_FIREBASE_UID = "auth_001";
    public static final String INVALID_LOGIN_CREDENTIALS = "login_004";

    private ErrorCode() {
    }
}
