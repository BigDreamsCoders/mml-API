package com.music.feed.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
@Table(name="album", schema = "public")
@JsonIdentityInfo(property = "token", generator = ObjectIdGenerators.UUIDGenerator::class)
data class Album(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "pg-uuid")
    @Column(name = "a_code", insertable = false)
    @JsonIgnore
    var code: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Song::class)
    @JoinColumn(name = "s_code", nullable = false)
    @OnDelete(action =  OnDeleteAction.CASCADE)
    var song : Song ?= null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Musician::class)
    @JoinColumn(name = "m_code", nullable = false)
    @OnDelete(action =  OnDeleteAction.CASCADE)
    var musician : Musician ?= null
) {
    constructor(song :Song, musician: Musician) : this(){
        this.song = song
        this.musician = musician
    }
}