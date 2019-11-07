package com.music.feed.domain

import org.hibernate.annotations.Type
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name="song", schema = "public")
data class Song(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Type(type = "pg-uuid")
        @Column(name = "s_code", insertable = false)
        var code: UUID? = null,

        @Column(name = "s_description")
        var description : String,

        @Column(name = "s_length")
        var length : String = "1 minute",
        @Column(name = "s_production_date")
        var productionDate : String = Date().toString(),

        @Column(name = "s_price")
        var price : BigDecimal = BigDecimal.ZERO,
        @Column(name = "s_song_title")
        var title : String = "default",

        @Column(name = "s_url")
        var url : String = "url",

        @ManyToMany(mappedBy = "songs")
        var musicians : Set<Musician>  =  HashSet()
        )