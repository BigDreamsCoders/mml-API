package com.music.feed.form

import org.hibernate.validator.constraints.Range
import javax.validation.constraints.NotEmpty

data class RateForm(
        @field:NotEmpty(message = "No empty song field")
        var code : String = "",
        @field:Range(min =0, max = 5, message = "Range out of bounds")
        var value : Int = 5
)