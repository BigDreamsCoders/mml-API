package com.music.feed.responses

data class TokenResponse(
        var message : String = "",
        var status : Int = 500,
        var token : String = ""
)