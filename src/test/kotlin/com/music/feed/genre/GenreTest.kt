package com.music.feed.genre

import com.music.feed.BaseTest
import com.music.feed.form.GenreForm
import org.junit.Assert
import org.junit.Test

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

class GenreTest : BaseTest() {
    val genreForm = GenreForm("test", "www.test.com", arrayListOf("n", "s") )

    @Test
    fun getGenres() {
        val uri = "genre/all"
        val mvcResult: MvcResult = mockMvc.perform(MockMvcRequestBuilders.get(base + uri)
                .accept(APPLICATION_JSON_UTF8)).andReturn()
        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString
        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
    }

    @Test
    fun createGenre(){
        val uri = "genre"
        val mvcResult: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post(base + uri)
                .content(mapToJson(genreForm))
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString
        Assert.assertEquals(201, status)
        Assert.assertNotNull(content)
    }
}