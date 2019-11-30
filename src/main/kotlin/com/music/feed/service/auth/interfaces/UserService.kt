package com.music.feed.service.auth.interfaces

import com.music.feed.domain.auth.User
import com.music.feed.form.UserForm
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserService  {
    fun save(user: User)
    fun save(user: UserForm, token : String):User
    fun saveNoCrypt(user:User)
    fun findByUsername(username: String): Optional<User>
    fun findByUsernameOrEmail (username: String, email : String) : Optional<User>
    fun findByEmail (email: String) : Optional<User>
    fun findByLoginToken(token : String) : Optional<User>
}