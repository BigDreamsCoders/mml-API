package com.music.feed.controller

import com.music.feed.form.LikeForm
import com.music.feed.form.RateForm
import com.music.feed.responses.GetResponse
import com.music.feed.responses.RequestResponse
import com.music.feed.service.RatingServiceImp
import com.music.feed.service.SongServiceImp
import com.music.feed.service.auth.UserServiceImp
import com.music.feed.util.JwtTokenUtil
import com.music.feed.validator.ErrorValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/rating")
class RatingController {
    @Autowired
    lateinit var errorValidator: ErrorValidator

    @Autowired
    lateinit var ratingService: RatingServiceImp

    @Autowired
    lateinit var userService: UserServiceImp

    @Autowired
    lateinit var songService: SongServiceImp

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    @PostMapping(value = ["/song"])
    @ResponseBody
    fun rateSong(request: HttpServletRequest,
            @Valid @RequestBody rateForm: RateForm,
            bindingResult : BindingResult): ResponseEntity<Any> {
        val errors = errorValidator.verifyBindingResult(bindingResult)
        if(errors.isPresent){
            return ResponseEntity(errors.get(), HttpStatus.UNPROCESSABLE_ENTITY)
        }
        val user = userService.findByEmail(jwtTokenUtil.getEmailFromToken(request))
        val song = songService.findByCode(UUID.fromString(rateForm.code))

        if(!user.isPresent){
            return ResponseEntity(RequestResponse("User not found: Issue with JWT given, please verify or refresh it", 500), HttpStatus.INTERNAL_SERVER_ERROR)
        }
        else if(!song.isPresent){
            return ResponseEntity(RequestResponse("Song code non existent", 500), HttpStatus.INTERNAL_SERVER_ERROR)
        }
        val cSong = song.get()
        val rating = ratingService.findByUserAndSong(user.get(), cSong)
        if(rating.isPresent){
            val ratingBefore : Double = rating.get().value.toDouble()*cSong.rated
            val ratingNow : Double = rateForm.value.toDouble()*cSong.rated
            val currentRating = cSong.rating.toDouble() * cSong.rated
            cSong.rating = BigDecimal(currentRating-ratingBefore+ratingNow)
                    .divide(BigDecimal(cSong.rated))
            rating.get().value = rateForm.value
            ratingService.save(rating.get(), rateForm)
        }
        else{
            val currentRate = cSong.rating.multiply(BigDecimal(cSong.rated))
            val rated = cSong.rated + 1
            val total = currentRate.add(BigDecimal(rateForm.value)).divide(BigDecimal(rated))

            cSong.rating = total
            cSong.rated = rated

            ratingService.save(user.get(),cSong, rateForm)
            songService.save(cSong)
        }

        return ResponseEntity("The rating was successfully registered", HttpStatus.OK)
    }

    @PostMapping(value = ["/song/like"])
    @ResponseBody
    fun songLikeStatus(request: HttpServletRequest, @Valid @RequestBody likeForm : LikeForm, bindingResult : BindingResult): ResponseEntity<Any> {
        val errors = errorValidator.verifyBindingResult(bindingResult)
        if(errors.isPresent){
            return ResponseEntity(errors.get(), HttpStatus.UNPROCESSABLE_ENTITY)
        }
        val user = userService.findByEmail(jwtTokenUtil.getEmailFromToken(request))
        val song = songService.findByCode(UUID.fromString(likeForm.code))

        if(!user.isPresent){
            return ResponseEntity(RequestResponse("User not found: Issue with JWT given, please verify or refresh it", 500), HttpStatus.INTERNAL_SERVER_ERROR)
        }
        else if(!song.isPresent){
            return ResponseEntity(RequestResponse("Song code non existent", 500), HttpStatus.INTERNAL_SERVER_ERROR)
        }
        val rating = ratingService.findByUserAndSong(user.get(), song.get())
        if(rating.isPresent){
            ratingService.save(rating.get(), likeForm)
        }
        else{
            ratingService.save(user.get(),song.get(), likeForm)
        }

        return ResponseEntity("The rating was successfully registered", HttpStatus.OK)
    }

    @GetMapping(value = ["/favorites"] )
    @ResponseBody
    fun getFavorites(request: HttpServletRequest) : ResponseEntity<Any>{
        val user = userService.findByEmail(jwtTokenUtil.getEmailFromToken(request))

        if(!user.isPresent){
            return ResponseEntity(RequestResponse("User not found: Issue with JWT given, please verify or refresh it", 500), HttpStatus.INTERNAL_SERVER_ERROR)
        }
        val ratings = ratingService.findByUserAndLikedStatus(user.get(), 1)
        val songs = ratings.map { it.song }
        return ResponseEntity(GetResponse("Favorites songs found" , 200, songs ), HttpStatus.OK)
    }
}