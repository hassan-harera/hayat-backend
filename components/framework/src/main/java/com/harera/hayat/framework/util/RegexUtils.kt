package com.harera.hayat.framework.util

object RegexUtils {

    @JvmStatic
    fun isUsername(subject: String): Boolean {
        return subject.matches(Regex.USERNAME.toRegex())
    }

    @JvmStatic
    fun isEmail(subject: String): Boolean {
        return subject.matches(Regex.EMAIL.toRegex())
    }

    @JvmStatic
    fun isPhoneNumber(subject: String): Boolean {
        return subject.matches(Regex.MOBILE.toRegex())
    }

    @JvmStatic
    fun isValidPassword(Password: String): Boolean {
        return Password.matches(Regex.PASSWORD.toRegex())
    }
}