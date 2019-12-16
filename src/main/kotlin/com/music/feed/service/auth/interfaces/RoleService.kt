package com.music.feed.service.auth.interfaces

import com.music.feed.domain.auth.Privilege
import com.music.feed.domain.auth.Role
import java.util.*

interface RoleService {
    fun findByName(name : String) : Optional<Role>
    fun save(name : String, privileges: Collection<Privilege> ) : Role
}