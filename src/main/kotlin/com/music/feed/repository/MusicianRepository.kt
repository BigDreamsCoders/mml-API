package com.music.feed.repository

import com.music.feed.domain.Musician
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MusicianRepository : CrudRepository<Musician, UUID> {

}