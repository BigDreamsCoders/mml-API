package com.music.feed.repository

import com.music.feed.domain.Song
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SongRepository : CrudRepository<Song, UUID> {

}