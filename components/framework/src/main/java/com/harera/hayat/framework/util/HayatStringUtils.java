package com.harera.hayat.framework.util;

public class HayatStringUtils {

    public static boolean isValidMobile(String mobile) {
        return (mobile.matches("^010[0-9]{8}$")) || (mobile.matches(("^011[0-9]{8}$")))
                        || (mobile.matches(("^012[0-9]{8}$")))
                        || (mobile.matches(("^015[0-9]{8}$")));
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    public static boolean isValidName(String name) {
        return name.length() >= 3;
    }

    public static boolean isValidEmail(String email) {
        return email.matches("^(.+)@(.+)$");
    }
}
