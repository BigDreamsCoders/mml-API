package com.music.feed

import com.music.feed.domain.auth.Privilege
import com.music.feed.domain.auth.Role
import com.music.feed.domain.auth.User
import com.music.feed.service.auth.PrivilegeServiceImp
import com.music.feed.service.auth.RoleServiceImp
import com.music.feed.service.auth.UserServiceImp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import javax.transaction.Transactional


@Component
class InitialDataLoader : ApplicationListener<ContextRefreshedEvent> {

    var alreadySetup = false

    @Autowired
    lateinit var userService: UserServiceImp

    @Autowired
    lateinit var roleService: RoleServiceImp

    @Autowired
    lateinit var privilegeService: PrivilegeServiceImp

    @Transactional
    override fun onApplicationEvent(p0: ContextRefreshedEvent) {
        if(alreadySetup)
            return
        val readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE")
        val writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE")

        val adminPrivileges = listOf(readPrivilege, writePrivilege)

        val adminDataRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges)
        createRoleIfNotFound("ROLE_USER", listOf(readPrivilege))

        val adminUser = User(names = "admin_test", username = "admintest", password = "admintest",
                email = "admintest@gmail.com", roles = listOf(adminDataRole))
        userService.save(adminUser)

    }

    @Transactional
    fun createPrivilegeIfNotFound(name : String) : Privilege{
        val privilege  = privilegeService.findByName(name)
        if (privilege.isEmpty) {
            return privilegeService.save(name)
        }
        return privilege.get()
    }

    @Transactional
    fun createRoleIfNotFound( name: String, privileges: Collection<Privilege>): Role {
        val role  = roleService.findByName(name)
        if (role.isEmpty) {
            return roleService.save(name, privileges)
        }
        return role.get()
    }
}