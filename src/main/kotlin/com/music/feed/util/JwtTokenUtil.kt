package com.music.feed.util

import com.music.feed.service.auth.RoleServiceImp
import com.music.feed.service.auth.UserServiceImp
import org.springframework.stereotype.Component
import java.io.Serializable
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors
import java.util.Calendar
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import javax.servlet.http.HttpServletRequest
import kotlin.collections.ArrayList


@Component
class JwtTokenUtil : Serializable {

    @Autowired
    private lateinit var userService: UserServiceImp

    @Autowired
    private lateinit var roleService: RoleServiceImp


    private val HEADER = "Authorization"
    private val PREFIX = "Bearer "
    private val SECRET = "mySecretKey"

    fun getEmailFromToken(request: HttpServletRequest): String {
        val jwtToken = request.getHeader(HEADER).replace(PREFIX, "")
        return Jwts.parser().setSigningKey(SECRET.toByteArray()).parseClaimsJws(jwtToken).body["sub"].toString()
    }

    //retrieve username from jwt token
    fun getEmailFromToken(token: String): String {
        return getClaimFromToken(token, Function { it.subject })
    }

    fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser().setSigningKey(SECRET.toByteArray()).parseClaimsJws(token).body
    }

    fun getJWTToken(username: String): String {
        var dt = Date()
        val c = Calendar.getInstance()
        c.time = dt
        c.add(Calendar.DATE, 7)
        dt = c.time

        val foundAuthorities = ArrayList<String>()
        val grantedAuthority = userService.findByEmail(username)

        if(grantedAuthority.isPresent){
            grantedAuthority.get().roles.forEach { role ->
                role.privileges.forEach {
                    foundAuthorities.add(it.name)
                }
            }
        }
        foundAuthorities.add("LOGIN_PRIVILEGE")

        val token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        foundAuthorities.stream()
                                .map { it }
                                .collect(Collectors.toList<Any>()))
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(dt)
                .signWith(SignatureAlgorithm.HS512,
                        SECRET.toByteArray()).compact()

        return "Bearer $token"
    }
}