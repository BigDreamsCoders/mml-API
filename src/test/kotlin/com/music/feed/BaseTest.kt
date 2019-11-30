package com.music.feed

import com.fasterxml.jackson.core.JsonParseException

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.junit.runner.RunWith
import org.springframework.web.context.WebApplicationContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.core.JsonProcessingException
import org.junit.Before
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import java.nio.charset.Charset
import javax.transaction.Transactional
import com.fasterxml.jackson.databind.SerializationFeature


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [MmlApplication::class])
@Transactional
abstract class BaseTest {

    protected val base = "/api/v1/"
    protected lateinit var mockMvc : MockMvc

    @Autowired
    lateinit var webApplicationContext : WebApplicationContext

    protected val APPLICATION_JSON_UTF8 = MediaType(MediaType.APPLICATION_JSON.type, MediaType.APPLICATION_JSON.subtype, Charset.forName("utf8"))


    @Before
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Throws(JsonProcessingException::class)
    protected fun mapToJson(obj: Any): String {
        val objectMapper = ObjectMapper()
        return objectMapper.writeValueAsString(obj)
    }

    @Throws(JsonParseException::class, JsonMappingException::class, IOException::class)
    protected fun <T> mapFromJson(json: String, clazz: Class<T>): T {
        val objectMapper = ObjectMapper()
        return objectMapper.readValue(json, clazz)
    }




}