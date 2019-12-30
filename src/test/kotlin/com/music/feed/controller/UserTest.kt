package com.music.feed.controller

import com.music.feed.BaseTest
import com.music.feed.domain.auth.User
import com.music.feed.form.UserForm
import org.junit.Assert
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.lang.Exception
import org.springframework.beans.factory.annotation.Autowired
import com.music.feed.service.auth.interfaces.UserService
import org.junit.Before

class UserTest : BaseTest() {

    @Autowired
    private lateinit var userService: UserService

    private val testUser = UserForm()

    @Before
    fun SetUp(){
        testUser.email = "test@test.com"
        testUser.password = "test"
    }

    @Test
    fun registerUser() {
        val userJson = mapToJson(testUser)

        val uri = "user/registration"
        val mvcResult: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post(base + uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_UTF8)
                .content(userJson)).andReturn()
        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString

        Assert.assertEquals(201, status)
        Assert.assertNotNull(content)
    }

    @Test
    @Throws(Exception::class)
    fun loginUser() {
        val userJson = mapToJson(testUser)

        val user = User()
        user.email = testUser.email
        user.password = testUser.password
        userService.save(user)

        val uri = "user/login"
        val mvcResult: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post(base + uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_UTF8).content(userJson)).andReturn()
        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString

        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
    }



    @Test
    fun deleteUser() {
        testUser.email = "test@test.com"
        val uri = "user/${testUser.email}"

        val user = User()
        user.email = testUser.email
        userService.save(user)

        val mvcResult: MvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(base + uri)
                .accept(MediaType.APPLICATION_JSON_VALUE).param("email",testUser.email)).andReturn()
        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString

        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
    }
}