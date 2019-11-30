package com.music.feed.controller

import com.music.feed.BaseTest
import com.music.feed.domain.Genre
import com.music.feed.domain.Musician
import com.music.feed.form.GenreForm
import com.music.feed.form.MusicianForm
import com.music.feed.service.GenreServiceImp
import com.music.feed.service.MusicianServiceImp
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

class MusicianTest : BaseTest() {

    @Autowired
    lateinit var genreService: GenreServiceImp

    @Autowired
    lateinit var musicianService : MusicianServiceImp

    var genre : Genre = Genre()

    @Before
    fun before(){
        val genreForm = GenreForm()
        genreForm.name = "Genre"
        genreForm.url = "http://test.com"
        genreForm.keywords = listOf("test", "pog")
        genre = genreService.save(genreForm)
    }

    @Test
    fun getAllMusicians(){
        val uri = "musician/all"
        val mvcResult: MvcResult = mockMvc.perform(MockMvcRequestBuilders.get(base + uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString
        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
    }

    @Test
    fun getMusician(){
        val musician = Musician()
        musician.genre = genre
        val created = musicianService.save(musician)

        val uri = "musician/${created.code}"

        val mvcResult: MvcResult = mockMvc.perform(MockMvcRequestBuilders.get(base + uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString
        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
    }

    @Test
    fun createMusician(){
        val uri = "musician"

        val musicianForm = MusicianForm()
        musicianForm.name = "Musi"
        musicianForm.surname = "Cian"
        musicianForm.genre = genre.code.toString()
        musicianForm.biography = "Rap test"

        val mvcResult : MvcResult = mockMvc.perform(MockMvcRequestBuilders.post(base+uri)
                .content(mapToJson(musicianForm))
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()

        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString
        Assert.assertEquals(201, status)
        Assert.assertNotNull(content)
    }
}