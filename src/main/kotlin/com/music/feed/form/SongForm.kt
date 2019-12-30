package com.music.feed.form

import com.music.feed.validator.annotation.NotBlankElements
import com.music.feed.validator.annotation.NotNullElements
import java.util.*
import javax.validation.constraints.NotEmpty

data class SongForm(
        @field:NotEmpty(message = "Empty name")
        var length : String = "1 minute",
        @field:NotEmpty(message = "Empty name")
        var description : String = "non",
        var date : String = Date().toString(),
        @field:NotEmpty(message = "Empty name")
        var title : String = "non",
        @field:NotEmpty(message = "Empty name")
        var thumbNail : String = "non",
        @field:NotEmpty(message = "Empty name")
        var youtubeLink : String = "non",
        @field:NotEmpty(message = "Empty name")
        var genre : String = "",
        @field:NotEmpty
        @field:NotBlankElements
        @field:NotNullElements
        var musicians : List<String> = ArrayList()

)