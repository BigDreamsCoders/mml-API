package com.music.feed.controller

import com.music.feed.BaseTest
import com.music.feed.domain.Genre
import com.music.feed.domain.Musician
import com.music.feed.domain.Song
import com.music.feed.form.GenreForm
import com.music.feed.form.MusicianForm
import com.music.feed.form.SongForm
import com.music.feed.form.UserForm
import com.music.feed.service.GenreServiceImp
import com.music.feed.service.MusicianServiceImp
import com.music.feed.service.SongServiceImp
import com.music.feed.service.auth.UserServiceImp
import com.music.feed.util.JwtTokenUtil
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.*

class SongTest : BaseTest() {
    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    private lateinit var userService : UserServiceImp

    @Autowired
    private lateinit var genreService: GenreServiceImp

    @Autowired
    private lateinit var musicianService: MusicianServiceImp

    @Autowired
    private lateinit var songService : SongServiceImp

    private var genre : Genre = Genre()

    private var musician : Musician = Musician()
    private var token:String = String()

    @Before()
    fun before(){
        val genreForm = GenreForm()
        genreForm.name = "Genre"
        genreForm.url = "http://test.com"
        genreForm.keywords = listOf("test", "pog")
        genre = genreService.save(genreForm)

        val musicianForm = MusicianForm()
        musicianForm.name = "Musi"
        musicianForm.surname = "Cian"
        musicianForm.genre = genre.code.toString()
        musicianForm.biography = "Rap test"
        musician = musicianService.save(musicianForm, genre)

        val userForm = UserForm()
        userForm.email = "test@test.com"
        userForm.password = "test"
        token = jwtTokenUtil.getJWTToken(userForm.email)
        userService.save(userForm, token)
    }

    @Test
    fun getAllSongs(){
        val uri = "song/all"
        val mvcResult: MvcResult = mockMvc.perform(MockMvcRequestBuilders.get(base + uri)
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString
        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
    }

    @Test
    fun getAllBestSongs(){
        val uri = "song/best"
        val mvcResult: MvcResult = mockMvc.perform(MockMvcRequestBuilders.get(base + uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString
        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
    }

    @Test
    fun getSong(){
       val song = Song()
        song.genre = genre
        val savedSong = songService.save(song)
        val uri = "song/${savedSong.code}"
        val mvcResult: MvcResult = mockMvc.perform(MockMvcRequestBuilders.get(base + uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString
        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
    }

    @Test
    fun createSong(){
        val uri = "song"
        val songForm = SongForm()
        songForm.title = "nice"
        songForm.date = Date().toString()
        songForm.description = "New and Fresh"
        songForm.genre = genre.code.toString()
        songForm.length = "30 minutes"
        songForm.thumbNail = "http://freshpicture.png"
        songForm.youtubeLink = "https://youtu.be/UH8g4sjeavU?list=RDW8kI1na3S2M"
        songForm.musicians = listOf(musician.code.toString())

        val mvcResult: MvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post(base + uri)
                .content(mapToJson(songForm))
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString
        Assert.assertEquals(201, status)
        Assert.assertNotNull(content)
    }
}