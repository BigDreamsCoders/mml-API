package com.music.feed.service.auth

import com.music.feed.domain.auth.Privilege
import com.music.feed.domain.auth.Role
import com.music.feed.repository.RoleRepository
import com.music.feed.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Primary
@Service
class UserDetailsServiceImp : UserDetailsService{

    @Autowired
    lateinit var userRepositoryImp: UserRepository

    @Autowired
    lateinit var roleRepositoryImp: RoleRepository


    @Transactional(readOnly = true)
    override fun loadUserByUsername( email : String): UserDetails{
        val user = userRepositoryImp.findByEmail(email)

        if(user.isPresent){
            val roles = user.get().roles
            return if(roles == null){
                val defaultRole = listOf(roleRepositoryImp.findByName("ROLE_USER").get())
                org.springframework.security.core.userdetails.User(user.get().email, user.get().password,
                        getAuthorities(defaultRole))
            } else{
                org.springframework.security.core.userdetails.User(user.get().email, user.get().password,
                        getAuthorities(roles))
            }
        }
        else{
            throw UsernameNotFoundException(email)
        }
    }

     fun getAuthorities(roles: Collection<Role>): Collection<GrantedAuthority?>? {
        return getGrantedAuthorities(getPrivileges(roles)!!)
    }

    private fun getPrivileges(roles: Collection<Role>): List<String>? {
        val privileges: MutableList<String> = ArrayList()
        val collection: MutableList<Privilege> = ArrayList<Privilege>()
        for (role in roles) {
            val privilege = role.privileges
            privilege?.let {
                collection.addAll(it)
            }
        }
        for (item in collection) {
            privileges.add(item.name)
        }
        return privileges
    }

    private fun getGrantedAuthorities(privileges: List<String>): List<GrantedAuthority>? {
        val authorities: MutableList<GrantedAuthority> = ArrayList()
        for (privilege in privileges) {
            authorities.add(SimpleGrantedAuthority(privilege))
        }
        return authorities
    }
}