package com.music.feed.responses

data class ErrorResponse(
        var message : String = "",
        var errors : List<Any> = ArrayList(),
        var status : Int = 500
)