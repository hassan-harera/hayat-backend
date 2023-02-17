package com.harera.hayat.util

import com.harera.hayat.framework.util.SubjectUtils
import org.junit.jupiter.api.Test
import javax.security.auth.Subject
import kotlin.test.assertEquals

class SubjectUtilsTest {

    @Test
    fun getSubject_withPhoneNumber_returnsPhoneNumber() {
        // given
        val subject = "01234567890"

        // when
        val result = SubjectUtils.getSubject(subject)

        // then
        assert(result is Subject.PhoneNumber)
    }

    @Test
    fun getSubject_withEmail_returnsEmail() {
        // given
        val subject = "ahmed@gmail.com"

        // when
        val result = SubjectUtils.getSubject(subject)

        // then
        assert(result is Subject.Email)
    }

    @Test
    fun getSubject_withUsername_returnsUsername() {
        // given
        val subject = "AhmedHassan"

        // when
        val result = SubjectUtils.getSubject(subject)

        // then
        assert(result is Subject.Username)
    }

    @Test
    fun getSubject_withInvalidSubject_returnsOther() {
        // given
        val subject = "aa"

        // when
        val result = SubjectUtils.getSubject(subject)

        // then
        assertEquals(Subject.Other::class, result::class)
    }
}