package com.music.feed.form.section

import javax.validation.constraints.NotEmpty

data class PhraseSection(
        @field:NotEmpty
        var name : String = ""
)