package com.music.feed.service.auth

import com.music.feed.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.HashSet
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Primary
@Service
class UserDetailsServiceImpl : UserDetailsService{

    @Autowired
    lateinit var userRepositoryImpl: UserRepository

    @Transactional(readOnly = true)
    override fun loadUserByUsername( email : String): UserDetails{
        val user = userRepositoryImpl.findByEmail(email)

        val grantedAuthorities = HashSet<GrantedAuthority>()
        if(user.isPresent){
            if(user.get().accountType==1) {
                grantedAuthorities.add(SimpleGrantedAuthority("client"))
            }
            return org.springframework.security.core.userdetails.User(user.get().email, user.get().password, grantedAuthorities)
        }
        else{
            throw UsernameNotFoundException(email)
        }
    }
}