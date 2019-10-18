package com.music.feed.domain

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

data class Phrase(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Type(type = "pg-uuid")
        @Column(name = "p_code", insertable = false)
        var code: UUID? = null,

        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phrase_p_id_seq")
        @SequenceGenerator(sequenceName = "phrase_p_id_seq", name = "phrase_p_id_seq", initialValue = 1, allocationSize = 1)
        @Column(name = "p_id", insertable = false)
        var localId: Int? = null,

        @OneToMany(mappedBy = "")
        @Column(name = "p_name")
        var name : String = ""
){

}