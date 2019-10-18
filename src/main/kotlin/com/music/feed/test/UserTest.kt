package com.music.feed.test

import com.music.feed.TestConfig
import com.music.feed.controller.UserController
import com.music.feed.form.UserForm
import com.music.feed.service.auth.UserServiceImpl
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.lang.Exception
import org.springframework.test.web.servlet.MockMvc
import org.springframework.beans.factory.annotation.Autowired



@AutoConfigureMockMvc
@ContextConfiguration(classes = [UserController::class, UserServiceImpl::class])
@WebMvcTest
class UserTest  {
    @Autowired
    private lateinit var mockMvc: MockMvc
    private val base = "api/v1/"

    @Test
    @Throws(Exception::class)
    fun registerUser() {
        val testUser : UserForm = UserForm()
        testUser.email = "000123456@uca.edu.sv"
        testUser.password = "testPassword"

        val uri  = "user/login"
        val mvcResult : MvcResult = mockMvc.perform(MockMvcRequestBuilders.post(base+uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()

        val status = mvcResult.response.status

        Assert.assertEquals(401, status)
        val content = mvcResult.response.contentAsString
        Assert.assertNotNull(content)
    }





}