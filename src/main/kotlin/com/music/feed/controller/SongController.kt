package com.music.feed.controller

import com.music.feed.domain.Song
import com.music.feed.form.SongForm
import com.music.feed.responses.GetResponse
import com.music.feed.responses.RequestResponse
import com.music.feed.responses.complex.LikesAndSong
import com.music.feed.service.GenreServiceImp
import com.music.feed.service.MusicianServiceImp
import com.music.feed.service.RatingServiceImp
import com.music.feed.service.SongServiceImp
import com.music.feed.service.auth.UserServiceImpl
import com.music.feed.util.JwtTokenUtil
import com.music.feed.validator.ErrorValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/song")
class SongController {
    @Autowired
    lateinit var errorValidator: ErrorValidator

    @Autowired
    lateinit var songService: SongServiceImp

    @Autowired
    lateinit var musicianService: MusicianServiceImp

    @Autowired
    lateinit var genreService : GenreServiceImp

    @Autowired
    lateinit var ratingService: RatingServiceImp

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    lateinit var userService: UserServiceImpl

    @GetMapping(value=["/all"])
    @ResponseBody
    fun getSongs(request: HttpServletRequest): ResponseEntity<Any> {
        val user = userService.findByEmail(jwtTokenUtil.getEmailFromToken(request))
        val songs = songService.findAll()
        return if(user.isPresent){
            val rating = ratingService.findByUserAndLikedStatus(user.get(), 1)

            val likes = rating.stream().map { it.code.toString() }.collect(Collectors.toSet())
            ResponseEntity(LikesAndSong(likes, songs), HttpStatus.OK)
        }
        else{
            ResponseEntity(songs, HttpStatus.OK)
        }
    }

    @GetMapping(value = ["/best"])
    @ResponseBody
    fun getSongsBestRated():List<Song>{
        return songService.findAllByOrderByRatedDesc().toList()
    }


    @GetMapping(value = ["/{code}"])
    @ResponseBody
    fun getSong(@PathVariable(name ="code") code : UUID) : ResponseEntity<Any> {
        val song = songService.findByCode(code)
        if(!song.isPresent){
            return ResponseEntity(RequestResponse("No song found matching that ID", 500), HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return ResponseEntity(GetResponse("Song found" , 200, song.get()), HttpStatus.OK)
    }

    @PostMapping
    @ResponseBody
    fun createSong(@Valid @RequestBody songForm: SongForm, bindingResult : BindingResult): ResponseEntity<Any> {
        val errors = errorValidator.verifyBindingResult(bindingResult)
        if(errors.isPresent){
            return ResponseEntity(errors.get(), HttpStatus.UNPROCESSABLE_ENTITY)
        }
        val genre = genreService.findByCode(UUID.fromString(songForm.genre))
        if(genre.isPresent){
            val musicians = musicianService.findAllByCodes(songForm.musicians)
            if(musicians.isNotEmpty()){
                songService.save(songForm, genre.get(), musicians)
                return ResponseEntity(RequestResponse("Song created" , 201), HttpStatus.CREATED)
            }
            return ResponseEntity(RequestResponse("Issue with musicians list" , 500), HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return ResponseEntity(RequestResponse("Error creating Song" , 500), HttpStatus.INTERNAL_SERVER_ERROR)
    }

}