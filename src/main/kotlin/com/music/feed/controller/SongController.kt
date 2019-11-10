package com.music.feed.controller

import com.music.feed.domain.Song
import com.music.feed.form.GenreForm
import com.music.feed.form.SongForm
import com.music.feed.responses.GetResponse
import com.music.feed.responses.RequestResponse
import com.music.feed.service.GenreServiceImp
import com.music.feed.service.MusicianServiceImp
import com.music.feed.service.SongServiceImp
import com.music.feed.validator.ErrorValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/song")
class SongController {
    @Autowired
    lateinit var errorValidator: ErrorValidator

    @Autowired
    lateinit var songServiceImp: SongServiceImp

    @Autowired
    lateinit var musicianServiceImp: MusicianServiceImp

    @Autowired
    lateinit var genreServiceImp : GenreServiceImp


    @GetMapping(value=["/all"])
    @ResponseBody
    fun getGenres():List<Song>{
        return songServiceImp.findAll()
    }


    @GetMapping(value = ["/{code}"])
    @ResponseBody
    fun getSong(@PathVariable(name ="code") code : UUID) : ResponseEntity<Any> {
        val song = songServiceImp.findByCode(code)
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
        val genre = genreServiceImp.findByCode(UUID.fromString(songForm.genre))
        if(genre.isPresent){
            val musicians = musicianServiceImp.findAllByCodes(songForm.musicians)
            if(musicians.isNotEmpty()){
                songServiceImp.save(songForm, genre.get(), musicians)
                return ResponseEntity(RequestResponse("Song created" , 200), HttpStatus.OK)
            }
            return ResponseEntity(RequestResponse("Issue with musicians list" , 500), HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return ResponseEntity(RequestResponse("Error creating Song" , 500), HttpStatus.INTERNAL_SERVER_ERROR)
    }

}