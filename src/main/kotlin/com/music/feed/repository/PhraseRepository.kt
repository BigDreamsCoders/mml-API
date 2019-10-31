package com.music.feed.repository

import com.music.feed.domain.Phrase
import org.springframework.data.repository.CrudRepository
import java.util.*

interface PhraseRepository : CrudRepository<Phrase, UUID> {
    fun findByName(name : String)
}