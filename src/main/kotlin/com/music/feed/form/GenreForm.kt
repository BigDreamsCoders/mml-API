package com.music.feed.form

import com.music.feed.annotation.NotBlankElements
import com.music.feed.annotation.NotNullElements
import com.music.feed.form.section.PhraseSection
import java.io.Serializable
import java.util.ArrayList
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class GenreForm (
        @field:NotEmpty(message = "Ingrese un nombre de genero")
        var name: String = "",
        @field:NotEmpty(message = "Ingrese un URL de imagen")
        var url: String = "",
        @field:NotEmpty
        @field:NotBlankElements
        @field:NotNullElements
        var keywords : List<String> = ArrayList()): Serializable  {
}