package com.music.feed.domain

import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDefs
import java.util.*
import javax.persistence.*

@Entity
@Table(name="musician", schema = "public")
data class Musician(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Type(type = "pg-uuid")
        @Column(name = "m_code", insertable = false)
        var code: UUID? = null,

        @Column(name = "m_name")
        var name : String = "name",

        @Column(name = "m_surname")
        var surname : String = "surname",

        @ManyToMany(cascade = [CascadeType.ALL])
        @JoinTable(
                name = "musician_song",
                joinColumns = [ JoinColumn(name = "m_code") ],
                inverseJoinColumns = [JoinColumn(name = "s_code") ]
        )
        var songs : Set<Song>  = HashSet()
        )