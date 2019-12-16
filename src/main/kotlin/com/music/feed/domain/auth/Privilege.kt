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

    var name: String = "NON_ROLE",

    @ManyToMany(mappedBy = "privileges")
    var roles: Collection<Role>? = null
)
{

}