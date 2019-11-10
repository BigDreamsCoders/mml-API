package com.music.feed.responses

import java.util.*

data class  GetResponse (
        var message : String = "",
        var status : Int = 500,
        var element : Any ?=null)