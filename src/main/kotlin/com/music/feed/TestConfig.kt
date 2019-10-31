package com.music.feed

import com.fasterxml.jackson.core.JsonParseException
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.context.WebApplicationContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.core.JsonProcessingException
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [MmlApplication::class])
@WebAppConfiguration
abstract class TestConfig {
    protected lateinit var mvc : MockMvc
    @Autowired
    lateinit var webApplicationContext : WebApplicationContext

    protected fun setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
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