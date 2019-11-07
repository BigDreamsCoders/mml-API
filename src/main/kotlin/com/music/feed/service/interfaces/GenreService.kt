package com.music.feed.service.interfaces

import com.music.feed.domain.Genre
import org.springframework.data.domain.Page
import java.util.*

interface GenreService {
    fun save(genre : Genre)
    fun findByName(name: String): Page<Genre>
    fun findByCode(code : UUID): Optional<Genre>
    fun findByNameIsLike(name: String) : Page<Genre>
    fun findByNameIsLikeAndPhrasesAndNameIsLike(name : String) : Page<Genre>
    fun delete(genre: Genre)
}