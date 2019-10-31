package com.music.feed.repository

import com.music.feed.domain.Genre
import com.music.feed.domain.Phrase
import com.music.feed.domain.auth.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GenreRepository : CrudRepository<Genre, UUID>{
    fun findByName(name: String, pageable:Pageable): Page<Genre>
    fun findByNameIsLike(name: String, pageable:Pageable) : Page<Genre>
    //fun findByNameIsLikeAndPhrasesAndNameIsLike(name : String, phrases:Set<Phrase>, pageable:Pageable) : Page<Genre>
}