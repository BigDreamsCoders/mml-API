package com.music.feed.domain.auth

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
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

    @Column(name = "p_name")
    var name: String = "NON_ROLE",

    @ManyToMany(mappedBy = "privileges")
    @JsonBackReference
    var roles: List<Role>? = null
)
{

}