package com.harera.hayat.authorization.util

sealed class Subject() {

    data class Email(val email: String) : Subject()
    data class PhoneNumber(val phoneNumber: String) : Subject()
    data class Username(val username: String) : Subject()
    object Other : Subject()
}
