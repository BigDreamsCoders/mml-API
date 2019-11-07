package com.music.feed.controller

import com.music.feed.service.SongServiceImp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/genre")
class SongController {
    @Autowired
    lateinit var songServiceImp: SongServiceImp

}