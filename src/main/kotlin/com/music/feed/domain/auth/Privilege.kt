package com.music.feed.domain.auth

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*


@Entity
data class Privilege (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "pg-uuid")
    @Column(name = "r_code", insertable = false)
    var code: UUID? = null,

    private val name: String? = null,

    @ManyToMany(mappedBy = "privileges")
    private val roles: Collection<Role>? = null
)