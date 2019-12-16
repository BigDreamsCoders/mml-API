package com.music.feed.repository

import com.music.feed.domain.auth.Privilege
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PrivilegeRepository : CrudRepository<Privilege, UUID> {
    fun findByName(name : String) : Optional<Privilege>
}