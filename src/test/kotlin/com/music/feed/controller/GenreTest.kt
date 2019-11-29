package com.music.feed.controller

import com.music.feed.BaseTest
import com.music.feed.form.GenreForm
import com.music.feed.service.GenreServiceImp
import org.junit.Assert
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

class GenreTest : BaseTest() {
    val genreForm = GenreForm("test", "www.test.com", arrayListOf("test", "lester") )
    val searchLike = "es"

    @Autowired
    lateinit var genreServiceImp: GenreServiceImp

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

    @Test
    fun findExactName(){
        genreServiceImp.save(genreForm)
        val uri = "genre/find?name=${genreForm.name}&type=1"
        val mvcResult : MvcResult = mockMvc.perform(MockMvcRequestBuilders.get(base+uri)
                .accept(APPLICATION_JSON_UTF8)).andReturn()
        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString
        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
    }

    @Test
    fun findLikeName(){
        genreServiceImp.save(genreForm)
        val uri = "genre/find?name=$searchLike&type=2"
        val mvcResult : MvcResult = mockMvc.perform(MockMvcRequestBuilders.get(base+uri)
                .accept(APPLICATION_JSON_UTF8)).andReturn()
        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString
        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
    }

    @Test
    fun findLikeAndPhraseLikeName(){
        genreServiceImp.save(genreForm)
        val uri = "genre/find?name=$searchLike&type=3"
        val mvcResult : MvcResult = mockMvc.perform(MockMvcRequestBuilders.get(base+uri)
                .accept(APPLICATION_JSON_UTF8)).andReturn()
        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString
        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
    }


}