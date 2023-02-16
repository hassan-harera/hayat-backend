package com.harera.hayat.authorization.util

object StringUtils {

    @JvmStatic
    fun isValidMobile(mobile: String): Boolean {
        return (mobile.matches("^010[0-9]{8}$".toRegex()) || mobile.matches("^011[0-9]{8}$".toRegex())
                || mobile.matches("^012[0-9]{8}$".toRegex())
                || mobile.matches("^015[0-9]{8}$".toRegex()))
    }

    @JvmStatic
    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    @JvmStatic
    fun isValidName(name: String): Boolean {
        return name.length >= 3
    }

    @JvmStatic
    fun isValidEmail(email: String): Boolean {
        return email.matches("^(.+)@(.+)$".toRegex())
    }
}