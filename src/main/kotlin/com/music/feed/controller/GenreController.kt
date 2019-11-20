package com.music.feed.controller

import com.music.feed.domain.Genre
import com.music.feed.form.GenreForm
import com.music.feed.responses.ErrorResponse
import com.music.feed.responses.GetResponse
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
        return ResponseEntity(RequestResponse("Genre created", 201), HttpStatus.CREATED)
    }

    @DeleteMapping(value = ["/{code}"])
    @ResponseBody
    fun deleteGenre(@PathVariable code : UUID) : ResponseEntity<Any> {
        val genre = genreServiceImp.findByCode(code)
        if(!genre.isPresent){
            return ResponseEntity(RequestResponse("No genre found matching that ID", 500), HttpStatus.INTERNAL_SERVER_ERROR)
        }
        genreServiceImp.delete(genre.get())
        return ResponseEntity(RequestResponse("Genre deleted", 200), HttpStatus.OK)
    }

    // 1: Exact name; 2: Like name; 3: Hard search of like name and like phrases
    @GetMapping(value = ["/find"])
    @ResponseBody
    fun findGenreWithName(@RequestParam(name = "name") name: String, @RequestParam(name="type") type : String) : ResponseEntity<Any>{
        when (type) {
            "1" -> {
                val genre = genreServiceImp.findByName(name)
                if(genre.isEmpty)
                    return ResponseEntity(RequestResponse("No genre found with that name", 500), HttpStatus.INTERNAL_SERVER_ERROR)
                return ResponseEntity(GetResponse("Genre found", 200, genre.get()), HttpStatus.OK)
            }
            "2" -> {
                val genres = genreServiceImp.findByNameIsLike(name)
                if(genres.isEmpty())
                    return ResponseEntity(RequestResponse("No genre found with that name", 500), HttpStatus.INTERNAL_SERVER_ERROR)
                return ResponseEntity(GetResponse("Genre found", 200, genres), HttpStatus.OK)
            }
            "3" -> {
                val genres = genreServiceImp.findByNameIsLikeAndPhrasesAndNameIsLike(name)
                if(genres.isEmpty())
                    return ResponseEntity(RequestResponse("No genre found with that name", 500), HttpStatus.INTERNAL_SERVER_ERROR)
                return ResponseEntity(GetResponse("Genre found", 200, genres), HttpStatus.OK)
            }
            else -> {
                return ResponseEntity(RequestResponse("Search type $type not supported", 500), HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }

    }


}
