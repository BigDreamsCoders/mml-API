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
        TypeDef(name="jsonb", typeClass = JsonBinaryType::class)
)
data class User (

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Type(type = "pg-uuid")
        @Column(name = "u_code", insertable = false)
        var code: UUID? = null,

        @Column(name = "u_email")
        var email: String = "",

        @Column(name = "u_username")
        var username: String = "",

        @Column(name = "u_password")
        var password: String = "",

        @Column(name = "u_account_type")
        var accountType: Int = 1,

        @Column(name = "u_token_verification")
        var verifyToken: String = "",

        @Column(name = "u_token_reset")
        var resetToken: String = "",

        @Column(name = "u_login_token")
        var loginToken : String = "",

        @Column(name = "u_active")
        var active: Boolean = true,

        @Column(name = "u_date_created", insertable = false)
        var dateCreated: String = Date().toString(),

        @Column(name = "u_profile_photo")
        var profilePhoto: String = "",

        @Column(name = "u_phone")
        @Type(type = "jsonb")
        var phone : JsonBinaryType ?= null,

        @Column(name = "u_names")
        var names : String  = "",

        @Column (name = "u_surname")
        var lastNames : String = "",

        @Column(name = "u_gender")
        var gender  : String = "not-specified",

        @Column(name = "u_last_updated")
        var lastUpdated : String = Date().toString(),

        @ManyToMany
        @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(
                name = "user_id", referencedColumnName = "u_code")],
        inverseJoinColumns = [JoinColumn(
                name = "role_id", referencedColumnName = "ro_code")])
        var roles : Collection<Role>?=null


){
}