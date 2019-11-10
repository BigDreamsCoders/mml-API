package com.music.feed.domain

import com.music.feed.domain.auth.User
import com.music.feed.form.LikeForm
import com.music.feed.form.RateForm
import com.vladmihalcea.hibernate.type.array.IntArrayType
import com.vladmihalcea.hibernate.type.array.StringArrayType
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.*
import java.util.*
import javax.persistence.*
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name="rating", schema = "public")
data class Rating(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Type(type = "pg-uuid")
        @Column(name = "r_code", insertable = false)
        var code: UUID? = null,

        @Column(name = "r_value")
        var value : Int = 5,

        @Column(name = "r_last_rated")
        var lastRated : String = Date().toString(),

        //0 for not specified, 1 for like and 2 for unlike
        @Column(name = "r_like_status")
        var likedStatus : Int = 0,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "u_code", nullable = false)
        @OnDelete(action =  OnDeleteAction.CASCADE)
        var user : User ?= null,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "s_code", nullable = false)
        @OnDelete(action =  OnDeleteAction.CASCADE)
        var song : Song ?= null

        ){
        constructor(user: User, song: Song, rateForm : RateForm) : this(){
                this.user = user
                this.song = song
                value = rateForm.value
        }
        constructor(user: User, song: Song, likeForm: LikeForm) : this(){
                this.user = user
                this.song = song
                likedStatus = likeForm.status
        }
}