package com.music.feed.domain

import com.fasterxml.jackson.annotation.*
import com.music.feed.form.MusicianForm
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDefs
import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


@Entity
@Table(name="musician", schema = "public")
@JsonIdentityInfo(property = "token", generator = ObjectIdGenerators.UUIDGenerator::class)
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

        @OneToMany(mappedBy = "musician", fetch = FetchType.LAZY)
        @JsonIgnore
        var album : MutableList<Album>  =  ArrayList()
        ){
        constructor(musicianForm: MusicianForm, genre: Genre) : this(){
                name = musicianForm.name
                surname = musicianForm.surname
                biography = musicianForm.biography
                this.genre = genre
        }
}