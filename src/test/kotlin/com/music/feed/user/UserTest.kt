package com.music.feed.user

import com.music.feed.MmlApplication
import com.music.feed.ServletInitializer
import com.music.feed.TestConfig
import com.music.feed.controller.UserController
import com.music.feed.form.UserForm
import com.music.feed.service.auth.UserServiceImpl
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.lang.Exception
import org.springframework.test.web.servlet.MockMvc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.AnnotationConfigContextLoader
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext






@RunWith(SpringJUnit4ClassRunner::class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = [ServletInitializer::class, AnnotationConfigContextLoader::class])
@WebMvcTest(controllers = [UserController::class])
class UserTest  {
    @Autowired
    private lateinit var mockMvc: MockMvc


    private val base = "api/v1/"
    private val testUser = UserForm()




    @Test
    @WithMockUser(username = "000123456@uca.edu.sv")
    @Throws(Exception::class)
    fun registerUser() {
        testUser.email = "000123456@uca.edu.sv"
        testUser.password = "testPassword"

        val uri  = "user/registration"
        val mvcResult : MvcResult = mockMvc.perform(MockMvcRequestBuilders.post(base+uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()

        val status = mvcResult.response.status

        Assert.assertEquals(401, status)
        val content = mvcResult.response.contentAsString
        Assert.assertNotNull(content)
    }

    @Test
    @Throws(Exception::class)
    fun loginUser(){
        testUser.email = "000123456@uca.edu.sv"
        testUser.password = "testPassword"

        val uri  = "user/login"
        val mvcResult : MvcResult = mockMvc.perform(MockMvcRequestBuilders.post(base+uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()

        val status = mvcResult.response.status

        Assert.assertEquals(200, status)
        val content = mvcResult.response.contentAsString
        Assert.assertNotNull(content)
    }

    @Test
    @Throws(Exception::class)
    fun deleteUser(){
        testUser.email = "000123456@uca.edu.sv"
        val uri  = "user/?email=${testUser.email}"

        val mvcResult : MvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(base+uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()

        val status = mvcResult.response.status

        Assert.assertEquals(200, status)
        val content = mvcResult.response.contentAsString
        Assert.assertNotNull(content)
    }
}