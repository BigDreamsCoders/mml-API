package com.music.feed.domain.auth

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*


@Entity
@Table(name="privilege", schema = "public")
data class Privilege (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "pg-uuid")
    @Column(name = "p_code", insertable = false)
    var code: UUID? = null,

    @Column(name = "p_name", insertable = false)
    var name: String = "NON_ROLE",

    @ManyToMany(mappedBy = "privileges")
    var roles: Collection<Role>? = null
)
{

}