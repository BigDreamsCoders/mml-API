package com.music.feed.util

import org.springframework.stereotype.Component
import java.io.Serializable
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import java.util.function.Function


@Component
class JwtTokenUtil : Serializable {
    private val SECRET = "mySecretKey"

    //retrieve username from jwt token
    fun getEmailFromToken(token: String): String {
        return getClaimFromToken(token, Function {it.subject })
    }

    fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).body
    }
}