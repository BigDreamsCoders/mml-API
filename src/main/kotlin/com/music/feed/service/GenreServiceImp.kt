package com.music.feed.service

import com.music.feed.domain.Genre
import com.music.feed.form.GenreForm
import com.music.feed.repository.GenreRepository
import com.music.feed.service.interfaces.GenreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class GenreServiceImp : GenreService{
    @Autowired
    lateinit var genreRepository: GenreRepository


    override fun save(genre : Genre){
        genreRepository.save(genre)
    }

    fun save(genreForm : GenreForm){
        val genre  = Genre(genreForm)
        genreRepository.save(genre)
    }

    fun findAll():List<Genre>{
        return genreRepository.findAll().toList()
    }

    override fun findByName(name: String): Page<Genre> {
        val page:Pageable = PageRequest.of(0,5)
        return genreRepository.findByName(name, page);
    }

    override fun findByNameIsLike(name: String): Page<Genre> {
        val page:Pageable = PageRequest.of(0,5)
        return genreRepository.findByNameIsLike(name, page)
    }

    override fun findByNameIsLikeAndPhrasesAndNameIsLike(name: String): Page<Genre> {
        val page:Pageable = PageRequest.of(0,5)
        return genreRepository.findAllMatches(name, page)
    }

    override fun findByCode(code: UUID): Optional<Genre> {
        return genreRepository.findByCode(code)
    }
    override fun delete(genre: Genre){
        genreRepository.delete(genre)
    }
}