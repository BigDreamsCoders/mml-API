package com.music.feed.service.interfaces

import com.music.feed.domain.Genre
import com.music.feed.domain.Musician
import com.music.feed.domain.Song
import com.music.feed.form.SongForm
import java.util.*

interface SongService {
    fun findAll():List<Song>
    fun findByCode(code : UUID) : Optional<Song>
    fun save(songForm: SongForm, genre: Genre, musicians: Set<Musician>)
}