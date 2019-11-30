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
import kotlin.collections.ArrayList

@Service
class GenreServiceImp : GenreService{
    @Autowired
    lateinit var genreRepository: GenreRepository


    override fun save(genre : Genre) : Genre{
        return genreRepository.save(genre)
    }

    override fun save(genreForm : GenreForm): Genre{
        val genre  = Genre(genreForm)
        return genreRepository.save(genre)
    }

    override fun findAll():List<Genre>{
        return genreRepository.findAll().toList()
    }

    override fun findByName(name: String): Optional<Genre> {
        return genreRepository.findByName(name)
    }

    override fun findByNameIsLike(name: String): List<Genre> {
        return genreRepository.findByNameContains(name)
    }

    override fun findByNameIsLikeAndPhrasesAndNameIsLike(name: String): List<Genre> {
        val genres = genreRepository.findAll().toList()
        //g.name.contains(minified)
        val regex = Regex("(.*)$name(.*)")
        val found :  MutableList<Genre> = ArrayList()
        for(g in genres){
            if(g.name.matches(regex) ){
                found.add(g)
                continue
            }
            else{
                for(phrase in g.keywords){
                    if(phrase.matches(regex)){
                        found.add(g)
                        break
                    }
                }
            }

        }
        return found.toList()
    }

    override fun findByCode(code: UUID): Optional<Genre> {
        return genreRepository.findByCode(code)
    }
    override fun delete(genre: Genre){
        genreRepository.delete(genre)
    }
}