package com.music.feed.domain.auth

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
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

    @Column(name = "ro_name")
    var name : String = "ROLE_USER",

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    var users : List<User>? = null ,

    @ManyToMany
    @JoinTable(
    name = "role_privilege",
    joinColumns =  [JoinColumn(name = "role_id", referencedColumnName = "ro_code")],
    inverseJoinColumns = [JoinColumn(name = "privilege_id", referencedColumnName = "p_code")])
    @JsonManagedReference
    var privileges : List<Privilege>? = null

){}