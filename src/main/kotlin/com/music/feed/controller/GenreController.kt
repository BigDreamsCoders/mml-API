package com.music.feed.controller

import com.music.feed.domain.Genre
import com.music.feed.form.GenreForm
import com.music.feed.responses.ErrorResponse
import com.music.feed.responses.RequestResponse
import com.music.feed.service.GenreServiceImp
import com.music.feed.validator.ErrorValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.stream.Collectors
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/genre")
class GenreController{
    @Autowired
    lateinit var errorValidator: ErrorValidator

    @Autowired
    lateinit var genreServiceImp: GenreServiceImp

    @GetMapping(value=["/all"])
    @ResponseBody
    fun getGenres():List<Genre>{
        return genreServiceImp.findAll()
    }

    @PostMapping
    @ResponseBody
    fun createGenre(@Valid @RequestBody genreForm: GenreForm, bindingResult : BindingResult): ResponseEntity<Any> {
        val errors = errorValidator.verifyBindingResult(bindingResult)
        if(errors.isPresent){
            return ResponseEntity(errors.get(), HttpStatus.UNPROCESSABLE_ENTITY)
        }
        genreServiceImp.save(genreForm)
        return ResponseEntity(RequestResponse("Genre created", 401), HttpStatus.CREATED)
    }
}
