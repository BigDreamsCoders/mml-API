package com.music.feed.form

import io.micrometer.core.lang.NonNull
import javax.validation.constraints.NotEmpty

data class MusicianForm(
        @field:NotEmpty(message = "Empty name")
        var name : String = "name",
        @field:NotEmpty(message = "Empty surname")
        var surname : String = "surname",
        @field:NotEmpty(message = "Empty biography")
        var biography : String = "non",
        @field:NotEmpty(message = "Empty genre")
        var genre : String = ""
)