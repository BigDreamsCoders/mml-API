package com.music.feed.controller

import com.music.feed.domain.Genre
import com.music.feed.form.GenreForm
import com.music.feed.responses.ErrorResponse
import com.music.feed.responses.RequestResponse
import com.music.feed.service.GenreServiceImp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/genre")
class GenreController{
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
        if (bindingResult.hasErrors()) {
            val problems : List<String> = bindingResult.fieldErrors.stream().map {
                "@" + it.field.toUpperCase() + ":" + it.defaultMessage }.collect(Collectors.toList())

            return ResponseEntity(ErrorResponse("Can't perform action due to errors",
                    problems,422), HttpStatus.UNPROCESSABLE_ENTITY)
        }
        return ResponseEntity(RequestResponse("Genre created", 401), HttpStatus.CREATED)
    }
}
