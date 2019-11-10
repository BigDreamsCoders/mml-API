package com.music.feed.form

import org.hibernate.validator.constraints.Range
import javax.validation.constraints.NotEmpty

data class LikeForm (
    @field:NotEmpty(message = "No empty song field")
    var code : String = "",
    @field:Range(min =0, max = 2, message = "Range out of bounds")
    var status : Int = 0
)