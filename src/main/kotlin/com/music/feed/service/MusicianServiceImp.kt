package com.music.feed.service

import com.music.feed.repository.MusicianRepository
import com.music.feed.service.interfaces.MusicianService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MusicianServiceImp : MusicianService {
    @Autowired
    lateinit var musicianRepository: MusicianRepository
}