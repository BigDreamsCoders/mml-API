package com.music.feed.controller

import com.music.feed.domain.Musician
import com.music.feed.form.GenreForm
import com.music.feed.form.MusicianForm
import com.music.feed.responses.GetResponse
import com.music.feed.responses.RequestResponse
import com.music.feed.service.GenreServiceImp
import com.music.feed.service.MusicianServiceImp
import com.music.feed.service.SongServiceImp
import com.music.feed.validator.ErrorValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/musician")
class MusicianController {
    @Autowired
    lateinit var errorValidator: ErrorValidator

    @Autowired
    lateinit var musicianServiceImp: MusicianServiceImp

    @Autowired
    lateinit var genreServiceImp : GenreServiceImp


    @GetMapping(value=["/all"])
    @ResponseBody
    fun getGenres():List<Musician>{
        return musicianServiceImp.findAll()
    }


    @GetMapping(value =["/{code}"])
    @ResponseBody
    fun selectMusician(@PathVariable(name = "code") code : UUID) : ResponseEntity<Any> {
        val musician = musicianServiceImp.findByCode(code)
        if(!musician.isPresent){
            return ResponseEntity(RequestResponse("No musician found matching that ID", 500), HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return ResponseEntity(GetResponse("Musician found" , 200, musician.get()), HttpStatus.OK)
    }

    @PostMapping
    @ResponseBody
    fun createMusician(@Valid @RequestBody musicianForm: MusicianForm, bindingResult : BindingResult): ResponseEntity<Any> {
        val errors = errorValidator.verifyBindingResult(bindingResult)
        if(errors.isPresent){
            return ResponseEntity(errors.get(), HttpStatus.UNPROCESSABLE_ENTITY)
        }
        val genre = genreServiceImp.findByCode(UUID.fromString(musicianForm.genre))
        if(genre.isPresent){
            musicianServiceImp.save(musicianForm, genre.get())
            return ResponseEntity(RequestResponse("Musician created" , 200), HttpStatus.OK)
        }
        return ResponseEntity(RequestResponse("Error creating musician" , 500), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}