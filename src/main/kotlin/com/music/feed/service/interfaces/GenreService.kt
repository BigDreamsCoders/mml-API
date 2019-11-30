package com.music.feed.service.interfaces

import com.music.feed.domain.Genre
import com.music.feed.form.GenreForm
import org.springframework.data.domain.Page
import java.util.*

interface GenreService {
    fun save(genre : Genre) : Genre
    fun save(genreForm : GenreForm) : Genre
    fun findAll():List<Genre>
    fun findByName(name: String): Optional<Genre>
    fun findByCode(code : UUID): Optional<Genre>
    fun findByNameIsLike(name: String) : List<Genre>
    fun findByNameIsLikeAndPhrasesAndNameIsLike(name : String) : List<Genre>
    fun delete(genre: Genre)
}