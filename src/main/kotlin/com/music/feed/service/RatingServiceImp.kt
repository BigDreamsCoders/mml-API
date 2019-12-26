package com.music.feed.service

import com.music.feed.domain.Rating
import com.music.feed.domain.Song
import com.music.feed.domain.auth.User
import com.music.feed.form.LikeForm
import com.music.feed.form.RateForm
import com.music.feed.repository.RatingRepository
import com.music.feed.service.interfaces.RatingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*

@Service
class RatingServiceImp : RatingService {
    @Autowired
    private lateinit var ratingRepository: RatingRepository

    override fun save(user: User, song: Song, rateForm : RateForm) {
        val newRating = Rating(user, song, rateForm)
        ratingRepository.save(newRating)
    }

    override fun save(user: User, song: Song, likeForm: LikeForm) {
        val newRating = Rating(user, song, likeForm)
        ratingRepository.save(newRating)
    }

    override fun save(rating: Rating, rateForm : RateForm) {
        rating.value = rateForm.value
        ratingRepository.save(rating)
    }

    override fun save(rating: Rating, likeForm: LikeForm) {
        rating.likedStatus = likeForm.status
        ratingRepository.save(rating)
    }

    override fun findByUserAndSong(user: User, song: Song): Optional<Rating> {
        return ratingRepository.findByUserAndSong(user,song)
    }

    override fun findByUserAndLikedStatus(user: User, likedStatus: Int): Set<Rating> {
        return ratingRepository.findByUserAndLikedStatus(user, likedStatus)
    }
}