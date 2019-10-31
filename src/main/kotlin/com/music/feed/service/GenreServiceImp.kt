package com.music.feed.service

import com.music.feed.domain.Genre
import com.music.feed.repository.GenreRepository
import com.music.feed.service.interfaces.GenreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class GenreServiceImp : GenreService{
    @Autowired
    lateinit var genreRepository: GenreRepository

    override fun save(genre : Genre){
        genreRepository.save(genre)
    }

    override fun findByName(name: String): Page<Genre> {
        return genreRepository.findByName(name)
    }

    override fun findByNameIsLike(name: String): Page<Genre> {
        return genreRepository.findByNameIsLike(name)
    }

    override fun findByNameIsLikeAndPhrasesAndNameIsLike(name: String): Page<Genre> {
        return genreRepository.findByNameIsLikeAndPhrasesAndNameIsLike(name)
    }
}