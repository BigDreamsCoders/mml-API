package com.music.feed.domain.auth

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
@Table(name="role", schema = "public")
data class Role (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "pg-uuid")
    @Column(name = "ro_code", insertable = false)
    var code: UUID? = null,

    @Column(name = "ro_name", insertable = false)
    var name : String = "ROLE_USER",

    @ManyToMany
    @JoinTable(
    name = "role_privilege",
    joinColumns =  [JoinColumn(name = "role_id", referencedColumnName = "ro_code")],
    inverseJoinColumns = [JoinColumn(name = "privilege_id", referencedColumnName = "p_code")])
    var privileges : Collection<Privilege>? = null

){}