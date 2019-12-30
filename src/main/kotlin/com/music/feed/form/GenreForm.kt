package com.music.feed.form

import com.music.feed.validator.annotation.NotBlankElements
import com.music.feed.validator.annotation.NotNullElements
import java.io.Serializable
import java.util.ArrayList
import javax.validation.constraints.NotEmpty

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