package com.music.feed.service.interfaces

import com.music.feed.domain.Genre
import com.music.feed.domain.Musician
import com.music.feed.form.MusicianForm
import com.music.feed.form.SongForm
import org.springframework.data.domain.Page
import java.awt.print.Pageable
import java.util.*

interface MusicianService {
    fun findAll():List<Musician>
    fun findByCode(code : UUID) : Optional<Musician>
    fun findAllByCodes(codes : List<String>) : Set<Musician>
    fun save(musicianForm: MusicianForm, genre: Genre )
}