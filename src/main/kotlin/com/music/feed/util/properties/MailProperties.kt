package com.music.feed.util.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import java.util.*

@Component
@PropertySource("classpath:mail.properties")
data class MailProperties(
        @Value("\${mail.smtp.auth}")
    var auth : Boolean ?= null,
        @Value("\${mail.smtp.connectiontimeout}")
    var connectionTimeout : Int ?= null,
        @Value("\${mail.smtp.timeout}")
    var timeout : Int ?= null,
        @Value("\${mail.smtp.writetimeout}")
    var writeTimeout : Int ?= null,
        @Value("\${mail.smtp.starttls.enable}")
        var startTlsEnable : Boolean ?= null,
        @Value("\${mail.smtp.starttls.required}")
        var startTlsRequired : Boolean ?= null

){
    fun createProperties() : Properties{
        val mailProperties = Properties()
        mailProperties["mail.smtp.auth"] = auth
        mailProperties["mail.smtp.connectiontimeout"] = connectionTimeout
        mailProperties["mail.smtp.timeout"] = timeout
        mailProperties["mail.smtp.writetimeout"] = writeTimeout
        mailProperties["mail.smtp.starttls.enable"] = startTlsEnable
        mailProperties["mail.smtp.starttls.required"] = startTlsRequired
        return mailProperties
    }
}

