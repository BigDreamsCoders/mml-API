package com.music.feed.repository

import com.music.feed.domain.Rating
import com.music.feed.domain.Song
import com.music.feed.domain.auth.User
import org.springframework.data.domain.Page
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RatingRepository : CrudRepository<Rating, UUID> {
    fun findByUserAndSong (user: User, song: Song) : Optional<Rating>
    fun findByUserAndLikedStatus(user: User, likedStatus: Int): Set<Rating>
}