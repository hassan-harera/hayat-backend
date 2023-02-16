package com.harera.hayat.authorization.util;

public final class Regex {

    public static final String MOBILE = "(?=(?:010|011|012|015)+(?:\\d{8})+).{11}";

    public static final String EMAIL =
                    "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

    public static final String USERNAME =
                    "^([A-Za-z]{1,15}[-_\\.]{0,1}[A-Za-z0-9_]{1,29}){3,30}$";

    public static final String PASSWORD =
                    "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

    private Regex() {

    }

}
