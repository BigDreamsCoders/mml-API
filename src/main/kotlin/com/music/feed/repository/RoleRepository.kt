package com.music.feed.repository

import com.music.feed.domain.auth.Role
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : CrudRepository<Role, UUID> {
    fun findByName(name : String) : Optional<Role>
}