package com.music.feed.util

import org.springframework.stereotype.Component
import java.io.Serializable
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.authority.AuthorityUtils
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors
import java.util.Calendar
import com.music.feed.util.JwtTokenUtil




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

     fun getJWTToken(username: String): String {
         var dt = Date()
         val c = Calendar.getInstance()
         c.time = dt
         c.add(Calendar.DATE, 7)
         dt = c.time

        val secretKey = "mySecretKey"
        val grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER")

        val token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map{ it.authority }
                                .collect(Collectors.toList<Any>()))
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(dt)
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.toByteArray()).compact()

        return "Bearer $token"
    }
}