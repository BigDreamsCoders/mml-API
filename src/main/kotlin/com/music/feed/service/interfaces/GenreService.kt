package com.music.feed.service.interfaces

import com.music.feed.domain.Genre
import com.music.feed.form.GenreForm
import org.springframework.data.domain.Page
import java.util.*

interface GenreService {
    fun save(genre : Genre)
    fun save(genreForm : GenreForm) : Genre
    fun findAll():List<Genre>
    fun findByName(name: String): Page<Genre>
    fun findByCode(code : UUID): Optional<Genre>
    fun findByNameIsLike(name: String) : Page<Genre>
    fun findByNameIsLikeAndPhrasesAndNameIsLike(name : String) : Page<Genre>
    fun delete(genre: Genre)
}