package com.music.feed.service.auth

import com.music.feed.domain.auth.User
import com.music.feed.repository.UserRepository
import com.music.feed.service.auth.interfaces.UserService
import org.springframework.stereotype.Service
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.beans.factory.annotation.Autowired
import java.util.*


@Service
class UserServiceImpl : UserService{

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    override fun save(user: User) {
        user.password=bCryptPasswordEncoder.encode(user.password)
        userRepository.save(user)
    }

    override fun findByUsername(username: String): Optional<User> {
        return userRepository.findByUsername(username)
    }

    override fun findByUsernameOrEmail(username: String, email: String): Optional<User> {
        return userRepository.findByUsernameOrEmail(username, email)
    }

    override fun findByEmail(email: String): Optional<User> {
        return userRepository.findByEmail(email)
    }
}