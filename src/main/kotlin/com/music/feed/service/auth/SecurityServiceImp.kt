package com.music.feed.service.auth

import com.music.feed.service.auth.interfaces.SecurityService
import org.slf4j.LoggerFactory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Service


@Service
class SecurityServiceImp : SecurityService {
    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userDetailsService : UserDetailsService

    override fun findLoggedInUsername(): String {
        val auth = SecurityContextHolder.getContext().authentication
        val username =  auth.name
        val details  = auth.details
        return if (details is WebAuthenticationDetails) username
        else return "not-found"
    }

    override fun autoLogin(username: String, password: String) {
        val userDetails = userDetailsService.loadUserByUsername(username)
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userDetails, password, userDetails.authorities)

        authenticationManager.authenticate(usernamePasswordAuthenticationToken)

        if (usernamePasswordAuthenticationToken.isAuthenticated) {
            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            logger.debug(String.format("Auto login %s successfully!", username))
        }
    }



    companion object {
        private val logger = LoggerFactory.getLogger(SecurityServiceImp::class.java)
    }
}