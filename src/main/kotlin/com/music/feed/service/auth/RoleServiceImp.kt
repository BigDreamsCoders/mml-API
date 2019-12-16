package com.music.feed.service.auth

import com.music.feed.domain.auth.Privilege
import com.music.feed.domain.auth.Role
import com.music.feed.repository.RoleRepository
import com.music.feed.service.auth.interfaces.RoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoleServiceImp : RoleService{
    @Autowired
    lateinit var roleRepository: RoleRepository

    override fun findByName(name: String): Optional<Role> {
        return roleRepository.findByName(name)
    }

    override fun save(name: String, privileges: Collection<Privilege>): Role {
        val role = Role(name = name, privileges = privileges)
        return roleRepository.save(role)
    }

}