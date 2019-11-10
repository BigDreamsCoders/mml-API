package com.music.feed.domain

import com.music.feed.form.GenreForm
import com.vladmihalcea.hibernate.type.array.IntArrayType
import com.vladmihalcea.hibernate.type.array.StringArrayType
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import java.util.*
import javax.persistence.*
import java.util.HashSet
import java.util.stream.Collectors
import kotlin.collections.ArrayList


//Entidad de Genero
@Entity
@Table(name="genre", schema = "public")
@TypeDefs(
        TypeDef(name = "int-array", typeClass = IntArrayType::class),
        TypeDef(name = "string-array", typeClass = StringArrayType::class),
        TypeDef(name="jsonb", typeClass = JsonBinaryType::class)
)
data class Genre(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Type(type = "pg-uuid")
        @Column(name = "c_code", insertable = false)
        var code: UUID? = null,

        @Column(name = "c_name")
        var name: String = "Genre",

        @Column(name = "c_url")
        var url : String = ".net",

        @Column(name = "c_popularity")
        var popularity: Int = 0,

        @Type(type = "string-array")
        @Column(name = "c_keywords", columnDefinition = "text[]")
        var keywords  : Array<String> = arrayOf("")

)
{
        constructor(genreForm: GenreForm) : this(){
                this.name = genreForm.name
                this.url = genreForm.url
                keywords= genreForm.keywords.toTypedArray()
        }

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Genre

                if (!keywords.contentEquals(other.keywords)) return false

                return true
        }

        override fun hashCode(): Int {
                return keywords.contentHashCode()
        }
}

