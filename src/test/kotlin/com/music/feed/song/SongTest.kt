package com.music.feed.song

import com.music.feed.BaseTest
import org.junit.Assert
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

class SongTest : BaseTest() {

    @Test
    fun getGenres() {
        val uri = "song/all"
        val mvcResult: MvcResult = mockMvc.perform(MockMvcRequestBuilders.get(base + uri)
                .accept(MediaType.APPLICATION_JSON)).andReturn()
        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString
        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
    }
}