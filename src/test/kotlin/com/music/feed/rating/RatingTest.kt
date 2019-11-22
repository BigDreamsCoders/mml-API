package com.music.feed.rating

import com.music.feed.BaseTest
import com.music.feed.domain.Genre
import com.music.feed.domain.Musician
import com.music.feed.domain.Song
import com.music.feed.domain.auth.User
import com.music.feed.form.*
import com.music.feed.service.auth.interfaces.UserService
import com.music.feed.service.interfaces.GenreService
import com.music.feed.service.interfaces.MusicianService
import com.music.feed.service.interfaces.SongService
import com.music.feed.util.JwtTokenUtil
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.math.BigDecimal
import java.util.*

class RatingTest : BaseTest() {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var songService: SongService

    @Autowired
    private lateinit var genreService: GenreService

    @Autowired
    private lateinit var musicianService: MusicianService

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    private val rateForm = RateForm()
    private var song:Song = Song()
    private var token:String = String()
    private var token2:String = String()

    @Before
    fun before() {
        val genreForm = GenreForm()
        genreForm.name = "Genre"
        val genre: Genre = genreService.save(genreForm)

        val musicianForm = MusicianForm()
        musicianForm.name = "Musician"
        val musician: Musician = musicianService.save(musicianForm, genre)

        val songForm = SongForm()
        songForm.title = "Song"
        song = songService.save(songForm, genre, mutableSetOf(musician))

        val userForm = UserForm()
        userForm.email = "test@test.com"
        userForm.password = "test"
        token = jwtTokenUtil.getJWTToken(userForm.email)
        val user: User = userService.save(userForm, token)

        userForm.email = "test2@test.com"
        userForm.password = "test"
        token2 = jwtTokenUtil.getJWTToken(userForm.email)
        val user2: User = userService.save(userForm, token)
    }

    @Test
    fun rateSong() {
        rateForm.code = song.code.toString()
        rateForm.value = 5
        var rateJson = mapToJson(rateForm)

        val uri = "rating/song"
        var mvcResult: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post(base + uri)
                .accept(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(rateJson)).andReturn()
        var status = mvcResult.response.status
        var content = mvcResult.response.contentAsString
        song = songService.findByCode(UUID.fromString(rateForm.code)).get();

        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
        Assert.assertEquals(1, song.rated)
        Assert.assertEquals(BigDecimal(5), song.rating)

        rateForm.value = 1
        rateJson = mapToJson(rateForm)

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(base + uri)
                .accept(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(rateJson)).andReturn()
        status = mvcResult.response.status
        content = mvcResult.response.contentAsString
        song = songService.findByCode(UUID.fromString(rateForm.code)).get();

        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
        Assert.assertEquals(1, song.rated)
        Assert.assertEquals(BigDecimal(1), song.rating)

        rateForm.value = 3
        rateJson = mapToJson(rateForm)

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(base + uri)
                .accept(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + token2)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(rateJson)).andReturn()
        status = mvcResult.response.status
        content = mvcResult.response.contentAsString
        song = songService.findByCode(UUID.fromString(rateForm.code)).get();

        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
        Assert.assertEquals(2, song.rated)
        Assert.assertEquals(BigDecimal(2), song.rating)
    }

    @Test
    fun getAllFavorites() {
        val uri = "rating/favorites"

        val mvcResult: MvcResult = mockMvc.perform(MockMvcRequestBuilders.get(base + uri)
                .header("Authorization", defaultToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString
        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
    }
}