package com.music.feed.controller

import com.music.feed.BaseTest
import com.music.feed.domain.Genre
import com.music.feed.domain.Musician
import com.music.feed.domain.Song
import com.music.feed.domain.auth.User
import com.music.feed.form.*
import com.music.feed.service.auth.interfaces.UserService
import com.music.feed.service.interfaces.GenreService
import com.music.feed.service.interfaces.MusicianService
import com.music.feed.service.interfaces.RatingService
import com.music.feed.service.interfaces.SongService
import com.music.feed.util.JwtTokenUtil
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.*

class FavoriteTest : BaseTest() {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var songService: SongService

    @Autowired
    private lateinit var genreService: GenreService

    @Autowired
    private lateinit var musicianService: MusicianService

    @Autowired
    private lateinit var ratingService: RatingService

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    private var token:String = String()
    private var song: Song = Song()
    private var song2: Song = Song()
    private var user: User = User()

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

        val songForm2 = SongForm()
        songForm2.title = "Song 2"
        song2 = songService.save(songForm, genre, mutableSetOf(musician))

        val userForm = UserForm()
        userForm.email = "test@test.com"
        userForm.password = "test"
        user = userService.save(userForm)
        token = jwtTokenUtil.getJWTToken(userForm.email)
    }

    @Test
    fun addFavorite(){
        var uri = "rating/song/like"
        val favoriteForm = LikeForm()
        favoriteForm.code = song.code.toString()
        favoriteForm.status = 0
        var favoriteJson = mapToJson(favoriteForm)

        var mvcResult: MvcResult = mockMvc.perform(MockMvcRequestBuilders.post(base + uri)
                .header("Authorization", "Bearer "+token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(favoriteJson)).andReturn()
        var status = mvcResult.response.status
        var content = mvcResult.response.contentAsString
        var rating = ratingService.findByUserAndSong(user, song)

        Assert.assertNotNull(rating)
        Assert.assertEquals(rating.get().likedStatus, 0)
        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)

        uri = "rating/song/like"
        favoriteForm.code = song.code.toString()
        favoriteForm.status = 1
        favoriteJson = mapToJson(favoriteForm)
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(base + uri)
                .header("Authorization", "Bearer "+token)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(favoriteJson)).andReturn()
        status = mvcResult.response.status
        content = mvcResult.response.contentAsString
        rating = ratingService.findByUserAndSong(user, song)

        Assert.assertEquals(200, status)
        Assert.assertNotNull(rating)
        Assert.assertEquals(rating.get().likedStatus, 1)
        Assert.assertNotNull(content)

        uri = "rating/favorites"
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(base + uri)
                .header("Authorization", "Bearer "+token)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        status = mvcResult.response.status
        content = mvcResult.response.contentAsString

        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
    }

    @Test
    fun getFavorites(){
        val uri = "rating/favorites"
        val mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(base + uri)
                .header("Authorization", "Bearer "+token)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString

        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
    }
}