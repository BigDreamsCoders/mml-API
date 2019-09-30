package com.music.feed.service.auth.interfaces

import com.music.feed.domain.auth.User
import java.util.*

interface UserService {
    fun save(user: User)

    fun findByUsername(username: String): Optional<User>
    fun findByUsernameOrEmail (username: String, email : String) : Optional<User>
    fun findByEmail (email: String) : Optional<User>
    fun findByLoginToken(token : String) : Optional<User>
    fun findByEmailAndPassword(email:String, password : String) : Optional<User>
}