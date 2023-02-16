package com.harera.hayat.authorization.security

import io.jsonwebtoken.Claims
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.function.Function

interface JwtService {

    fun extractExpiration(token: String?): Date
    fun <T> extractClaim(token: String?, claimsResolver: Function<Claims, T>): T
    fun extractAllClaims(token: String?): Claims
    fun generateAdminToken(userDetails: UserDetails): String
    fun generateUserToken(userDetails: UserDetails): String
    fun generateDriverToken(userDetails: UserDetails): String
    fun createToken(claims: Map<String, Any>, subject: String, millis: Int): String
    fun validateToken(token: String?, userDetails: UserDetails): Boolean
    fun isTokenExpired(token: String?): Boolean
    fun createToken(
        claims: Map<String, Any>,
        subject: String,
        date: Date
    ): String

    fun extractUsernameFromAuthorization(authorization: String): String
    fun extractUsernameFromBearerToken(token: String): String
    fun generateUserToken(uid: Int): String
}
