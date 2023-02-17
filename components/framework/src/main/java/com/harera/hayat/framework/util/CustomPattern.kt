package com.harera.hayat.framework.util

object CustomPattern {

    const val CITY_ID = "^\\d{1,5}$"
    const val MEDICINE_ID = "^\\d{1,6}$"
    const val PACKAGE_ID = "^\\d{1,5}$"
    const val QUANTITY = "^\\d{1,5}\\.\\d{1}$"
    const val DESCRIPTION = "^.{0,500}$"
    const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
    const val DATE_FORMAT = "^\\d{4}-\\d{1,2}-\\d{1,2}\$"
}