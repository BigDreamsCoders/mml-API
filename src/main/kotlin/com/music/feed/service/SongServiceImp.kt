package com.music.feed.service

import com.music.feed.domain.Genre
import com.music.feed.domain.Musician
import com.music.feed.domain.Song
import com.music.feed.form.SongForm
import com.music.feed.repository.SongRepository
import com.music.feed.service.interfaces.SongService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class SongServiceImp : SongService{
    @Autowired
    lateinit var songRepository: SongRepository

    override fun findByCode(code: UUID): Optional<Song> {
        return songRepository.findByCode(code)
    }

    override fun save(songForm: SongForm, genre: Genre, musicians: Set<Musician>) {
        val song = Song(songForm, genre, musicians)
        songRepository.save(song)
    }

    override fun findAll(): List<Song> {
        return songRepository.findAll().toList()
    }

}