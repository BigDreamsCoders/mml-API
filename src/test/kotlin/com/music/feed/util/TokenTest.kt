package com.music.feed.util

import com.music.feed.BaseTest
import com.music.feed.domain.auth.User
import com.music.feed.form.UserForm
import com.music.feed.service.auth.SecurityServiceImp
import com.music.feed.service.auth.interfaces.UserService
import io.jsonwebtoken.Claims
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.function.Function
import org.springframework.mock.web.MockFilterChain
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.mock.web.MockHttpServletRequest
import org.mockito.InjectMocks






class TokenTest : BaseTest() {
    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    lateinit var  securityService : SecurityServiceImp

    private var username = "00062815@uca.edu.sv"
    private val PREFIX = "Bearer "

    private val testUser = UserForm()

    @InjectMocks
    var jwtAuthorizationFilter : JWTAuthorizationFilter = JWTAuthorizationFilter()

    @Before
    fun setUp(){
        testUser.email = "test@test.com"
        testUser.password = "test"
    }

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


    @Test
    fun checkToken(){
        val uri = "user/token"

        userService.save(testUser)
        val token = jwtTokenUtil.getJWTToken(testUser.email)

        val mvcResult: MvcResult = mockMvc.perform(MockMvcRequestBuilders.get(base + uri)
                .header("Authorization", "Bearer $token")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()

        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString

        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
    }

    @Test
    fun failToken(){
        val request = MockHttpServletRequest()
        request.addHeader("authorization", "Bearer sd")
        request.requestURI = "$base/genre/all"
        val response =  MockHttpServletResponse()
        val filterChain = MockFilterChain()


        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain)
        val status = response.status
        Assert.assertEquals(status, 403)
    }
    @Test
    fun succeedToken(){
        val user = User()
        user.email = testUser.email
        user.password = testUser.password
        userService.save(user)
        val token = jwtTokenUtil.getJWTToken(testUser.email)

        val request = MockHttpServletRequest()
        request.addHeader("authorization", token)
        request.requestURI = "$base/genre/all"
        val response =  MockHttpServletResponse()
        val filterChain = MockFilterChain()


        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain)
        val status = response.status
        Assert.assertEquals(status, 200)
    }
}