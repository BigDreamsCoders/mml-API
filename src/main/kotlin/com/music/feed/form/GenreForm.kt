package com.music.feed.form

import com.music.feed.form.section.PhraseSection
import java.util.ArrayList
import javax.validation.constraints.NotEmpty

data class GenreForm(
        @field:NotEmpty(message = "Ingrese un nombre de genero")
        var name : String = "",

        var phrases : List<PhraseSection> = ArrayList()
){

}