package com.music.feed.service.auth.interfaces

import com.music.feed.domain.auth.Privilege
import java.util.*

interface PrivilegeService {
    fun findByName(name : String) : Optional<Privilege>
    fun save(name : String) : Privilege
}