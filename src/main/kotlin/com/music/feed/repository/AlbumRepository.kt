package com.music.feed.repository

import com.music.feed.domain.Album
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AlbumRepository : CrudRepository<Album, UUID> {
}