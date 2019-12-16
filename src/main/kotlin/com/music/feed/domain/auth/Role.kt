package com.music.feed.domain.auth

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
data class Role (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "pg-uuid")
    @Column(name = "r_code", insertable = false)
    var code: UUID? = null,
    var name : String = "ROLE_USER",
    @ManyToMany
    @JoinTable(
    name = "roles_privileges",
    joinColumns =  [JoinColumn(name = "role_id", referencedColumnName = "id")],
    inverseJoinColumns = [JoinColumn(name = "privilege_id", referencedColumnName = "id")])
    var privileges : Collection<Privilege>? = null

){}