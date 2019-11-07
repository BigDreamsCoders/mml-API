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
    fun findByName(name: String, pageable:Pageable): Page<Genre>
    fun findByNameIsLike(name: String, pageable:Pageable) : Page<Genre>
    @Query(nativeQuery = true, value = "select c_code, c_id, c_name, c_url, c_popularity, " +
            "unrest(c_keywords) from Genre g WHERE g.name LIKE concat('%', :name, '%') and " +
            "lower(:name) :name LIKE ALL(g.keywords)" )
    fun findAllMatches(@Param("name") name : String, pageable:Pageable) : Page<Genre>
}