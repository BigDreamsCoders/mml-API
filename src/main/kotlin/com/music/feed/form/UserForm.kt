package com.music.feed.form

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class UserForm(
        @field:NotEmpty(message = "Empty email")
       // @field:NotBlank
        @field:Email(message = "Bad email format")
        var email : String = "",
        @field:NotEmpty(message = "Empty password")
        //@field:NotBlank
        var password : String = ""
)