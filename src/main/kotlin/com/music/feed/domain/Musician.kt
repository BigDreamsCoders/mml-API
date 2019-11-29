package com.music.feed.domain

import com.music.feed.form.MusicianForm
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
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

        @Column(name = "m_biography")
        var biography : String = "non",

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "c_code", nullable = false)
        @OnDelete(action =  OnDeleteAction.CASCADE)
        var genre : Genre ?= null,

        @ManyToMany(cascade = [CascadeType.ALL])
        @JoinTable(
                name = "musician_song",
                joinColumns = [ JoinColumn(name = "m_code") ],
                inverseJoinColumns = [JoinColumn(name = "s_code") ]
        )
        var songs : MutableSet<Song>  = HashSet()
        ){
        constructor(musicianForm: MusicianForm, genre: Genre) : this(){
                name = musicianForm.name
                surname = musicianForm.surname
                biography = musicianForm.biography
                this.genre = genre
        }
}