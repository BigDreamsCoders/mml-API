package com.music.feed.service.auth

import com.music.feed.domain.auth.Privilege
import com.music.feed.repository.PrivilegeRepository
import com.music.feed.service.auth.interfaces.PrivilegeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PrivilegeServiceImp : PrivilegeService {
    @Autowired
    private lateinit var privilegeRepository: PrivilegeRepository
    override fun findByName(name: String): Optional<Privilege> {
        return privilegeRepository.findByName(name)
    }

    override fun save(name: String): Privilege {
        val privilege = Privilege(name = name)
        return privilegeRepository.save(privilege)
    }
}