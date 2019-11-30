package com.music.feed.service

import com.music.feed.domain.Genre
import com.music.feed.domain.Musician
import com.music.feed.domain.Song
import com.music.feed.form.MusicianForm
import com.music.feed.form.SongForm
import com.music.feed.repository.MusicianRepository
import com.music.feed.service.interfaces.MusicianService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Service
class MusicianServiceImp : MusicianService {
    @Autowired
    lateinit var musicianRepository: MusicianRepository

    override fun findByCode(code: UUID): Optional<Musician> {
        return musicianRepository.findByCode(code)
    }

    override fun save(musician: Musician) : Musician {
        return musicianRepository.save(musician)
    }

    override fun save(musicianForm: MusicianForm, genre: Genre ):Musician {
        val musician = Musician(musicianForm, genre)
        return musicianRepository.save(musician)
    }

    override fun findAllByCodes(codes : List<String>) : Set<Musician>{
        val found :  HashMap<Musician, Int> = HashMap()
        var aux : Optional<Musician>
        codes.forEach {
            aux = musicianRepository.findByCode(UUID.fromString(it))
            aux.ifPresent {
                found[aux.get()] = 1
            }
        }
        return found.keys.toSet()
    }

    override fun findAll(): List<Musician> {
        return musicianRepository.findAll().toList()
    }
}
