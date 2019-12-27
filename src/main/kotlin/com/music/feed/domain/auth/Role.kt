package com.music.feed.domain.auth

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Type
import java.util.*
import java.util.stream.Collectors
import javax.persistence.*
import kotlin.collections.ArrayList

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

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JsonIgnore
    var users : List<User>? = null ,

    @ManyToMany
    @JoinTable(
    name = "role_privilege",
    joinColumns =  [JoinColumn(name = "role_id", referencedColumnName = "ro_code")],
    inverseJoinColumns = [JoinColumn(name = "privilege_id", referencedColumnName = "p_code")])
    @JsonBackReference
    var privileges : Collection<Privilege> = ArrayList()

){
    override fun toString(): String {
        return "Role{" +
                "code=${code},"+
                "name=${name},"+
                "privileges="+privileges.stream().map(Privilege::name).collect(Collectors.toList())+
                "}"
    }
}