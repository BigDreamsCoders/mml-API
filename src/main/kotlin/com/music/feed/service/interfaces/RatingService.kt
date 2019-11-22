package com.music.feed.service.interfaces

import com.music.feed.domain.Rating
import com.music.feed.domain.Song
import com.music.feed.domain.auth.User
import com.music.feed.form.LikeForm
import com.music.feed.form.RateForm
import org.springframework.data.domain.Page
import java.util.*

interface RatingService{
    fun save(rating: Rating, likeForm: LikeForm)
    fun save(rating: Rating, rateForm : RateForm)
    fun save(user: User, song : Song, rateForm : RateForm)
    fun save(user: User, song: Song, likeForm: LikeForm)
    fun findByUserAndSong (user: User, song: Song) : Optional<Rating>
    fun findByUserAndLikedStatus(user: User, likedStatus: Int): Set<Rating>
}