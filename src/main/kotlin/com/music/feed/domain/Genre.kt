package com.music.feed.domain

import com.vladmihalcea.hibernate.type.array.IntArrayType
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import java.util.*
import javax.persistence.*

@Entity
@Table(name="user", schema = "public")
@TypeDefs(
        TypeDef(name = "int-array", typeClass = IntArrayType::class),
        TypeDef(name="jsonb", typeClass = JsonBinaryType::class)
)
data class Genre(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Type(type = "pg-uuid")
        @Column(name = "c_code", insertable = false)
        var code: UUID? = null,

        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "code_c_id_seq")
        @SequenceGenerator(sequenceName = "code_c_id_seq", name = "code_c_id_seq", initialValue = 1, allocationSize = 1)
        @Column(name = "c_id", insertable = false)
        var localId: Int? = null,

        @Column(name = "c_name")
        var name: String = "Genre",

        @Column(name = "c_popularity")
        var popularity: Int = 0,

        @Column(name = "c_phrases")
        var phrases: Set<Phrase>? = null
)
{}

