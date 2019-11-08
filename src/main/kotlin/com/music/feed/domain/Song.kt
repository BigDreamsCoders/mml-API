package com.music.feed.domain

import com.music.feed.form.SongForm
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
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
        var description : String = "non",

        @Column(name = "s_length")
        var length : String = "1 minute",

        @Column(name = "s_production_date")
        var productionDate : String = Date().toString(),

        @Column(name = "s_rating")
        var rating : BigDecimal = BigDecimal.ZERO,

        @Column(name = "s_song_title")
        var title : String = "default",

        @Column(name = "s_rated")
        var rated : Int = 0,

        @Column(name = "s_thumb_nail")
        var thumbNail : String = "url",

        @Column(name ="s_youtube")
        var youtubeLink : String = "",

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "c_code", nullable = false)
        @OnDelete(action =  OnDeleteAction.CASCADE)
        var genre : Genre ?= null,

        @ManyToMany(mappedBy = "songs")
        var musicians : Set<Musician>  =  HashSet()


        ){
        constructor(songForm: SongForm, genre: Genre, musicians: Set<Musician>) : this() {
                length = songForm.length
                description = songForm.description
                productionDate = songForm.date
                title = songForm.title
                thumbNail = songForm.thumbNail
                youtubeLink = songForm.youtubeLink
                this.genre = genre
                this.musicians = musicians
        }
}