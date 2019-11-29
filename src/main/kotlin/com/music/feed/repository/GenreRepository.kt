package com.music.feed.repository

import com.music.feed.domain.Genre
import com.music.feed.domain.auth.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GenreRepository : CrudRepository<Genre, UUID>{
    fun findByCode(code : UUID): Optional<Genre>
    fun findByName(name: String): Optional<Genre>
    fun findByNameContains(name: String) : List<Genre>
}