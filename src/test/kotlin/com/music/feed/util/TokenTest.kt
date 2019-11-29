package com.music.feed.util

import com.music.feed.BaseTest
import io.jsonwebtoken.Claims
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.function.Function

class TokenTest : BaseTest() {
    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    private var username = "00062815@uca.edu.sv"
    private val PREFIX = "Bearer "

    @Test
    fun createToken(){
        val expectedToken = jwtTokenUtil.getJWTToken(username)
        Assert.assertNotNull(expectedToken)
    }

    @Test
    fun extractUsername(){
        val response = jwtTokenUtil.getJWTToken(username).replace(PREFIX, "")
        print(response)
        val result = jwtTokenUtil.getEmailFromToken(response)
        Assert.assertEquals(username, result)
    }

    @Test
    fun getAllClaimsFromToken(){
        val response = jwtTokenUtil.getJWTToken(username).replace(PREFIX, "")
        print(response)
        val claims : Function<Claims, String> = Function { it.subject }
        val result = jwtTokenUtil.getClaimFromToken(response, claims)
        Assert.assertEquals(username, result)
    }
}