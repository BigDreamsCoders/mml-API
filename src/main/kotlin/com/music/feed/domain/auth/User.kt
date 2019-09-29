package com.music.feed.domain.auth

import com.vladmihalcea.hibernate.type.array.IntArrayType
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name="user", schema = "public")
@TypeDefs(
        TypeDef(name = "int-array", typeClass = IntArrayType::class),
        TypeDef(name="jsonb", typeClass = JsonBinaryType::class)
)
data class User (

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Type(type = "pg-uuid")
        @Column(name = "u_code", insertable = false)
        var code: UUID? = null,

        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_u_id_seq")
        @SequenceGenerator(sequenceName = "user_u_id_seq", name = "user_u_id_seq", initialValue = 1, allocationSize = 1)
        @Column(name = "u_id", insertable = false)
        var localId: Int? = null,

        @Column(name = "u_email")
        var email: String = "",

        @Column(name = "u_username")
        var username: String = "",

        @Column(name = "u_password")
        var password: String = "",

        @Column(name = "u_account_type")
        var accountType: Int = 1,

        @Column(name = "u_token_verification")
        @Type(type = "pg-uuid")
        var verifyToken: UUID ?= null,

        @Column(name = "u_token_reset")
        @Type(type = "pg-uuid")
        var resetToken: UUID ?= null,

        @Column(name = "u_login_token")
        @Type(type = "pg-uuid")
        var loginToken : UUID ?= null,

        @Type(type = "int-array")
        @Column(name = "u_favorites", columnDefinition = "integer[]")
        var favorites: IntArray = intArrayOf(),

        @Type(type = "int-array")
        @Column(name = "u_not_liked", columnDefinition = "integer[]")
        var unliked: IntArray = intArrayOf(),

        @Column(name = "u_active")
        var active: Boolean = true,

        @Column(name = "u_date_created", insertable = false)
        var dateCreated: String = "",

        @Column(name = "u_profile_photo")
        var profilePhoto: String = "",

        @Column(name = "u_address")
        @Type(type = "jsonb")
        var address : JsonBinaryType ?= null,

        @Column(name = "u_document")
        @Type(type = "jsonb")
        var document : JsonBinaryType ?= null,

        @Column(name = "u_phone")
        @Type(type = "jsonb")
        var phone : JsonBinaryType ?= null,

        @Column(name = "u_names")
        var names : String ? = null,

        @Column (name = "u_lastanmes")
        var lastNames : String ?= null,

        @Column(name = "u_sex")
        var sex  : Boolean = false,

        @Column(name = "u_currently_hired")
        var hired  : Boolean = false,

        @Column(name = "u_last_updated")
        var lastUpdated : String ?= null

) : Serializable
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (favorites.contentEquals(other.favorites)) return true
        if (unliked.contentEquals(other.unliked)) return true

        return false
    }

    override fun hashCode(): Int {
        var result = favorites.hashCode()
        result = 31 * result + favorites.contentHashCode()
        return result
    }
}