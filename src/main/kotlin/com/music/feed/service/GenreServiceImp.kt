package com.music.feed.service

import com.music.feed.domain.Genre
import com.music.feed.domain.Phrase
import com.music.feed.repository.GenreRepository
import com.music.feed.service.interfaces.GenreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class GenreServiceImp : GenreService{
    @Autowired
    lateinit var genreRepository: GenreRepository

    override fun save(genre : Genre){
        genreRepository.save(genre)
    }

    override fun findByName(name: String): Page<Genre> {
        val page:Pageable = PageRequest.of(0,5)
        return genreRepository.findByName(name, page);
    }

    override fun findByNameIsLike(name: String): Page<Genre> {
        val page:Pageable = PageRequest.of(0,5)
        return genreRepository.findByNameIsLike(name, page)
    }

    /*override fun findByNameIsLikeAndPhrasesAndNameIsLike(name: String): Page<Genre> {
        val page:Pageable = PageRequest.of(0,5)
        val phrases = setOf<Phrase>();
        return genreRepository.findByNameIsLikeAndPhrasesAndNameIsLike(name, phrases, page)
    }*/
}