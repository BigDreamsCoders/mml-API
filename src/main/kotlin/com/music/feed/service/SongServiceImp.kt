package com.music.feed.service

import com.music.feed.repository.SongRepository
import com.music.feed.service.interfaces.SongService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SongServiceImp : SongService{
    @Autowired
    lateinit var songRepository: SongRepository
}