package com.music.feed.controller

import com.music.feed.service.MusicianServiceImp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/genre")
class MusicianController {
    @Autowired
    lateinit var musicianServiceImp: MusicianServiceImp

}