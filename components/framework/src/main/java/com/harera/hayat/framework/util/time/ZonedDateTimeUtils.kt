package com.harera.hayat.framework.util.time

import java.time.OffsetDateTime

object OffsetDateTimeUtils {

    const val FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"

    fun isValidOffsetDateTime(string: String): Boolean {
        try {
            OffsetDateTime.parse(string)
            return true
        } catch (e: Exception) {
            return false
        }
    }

}