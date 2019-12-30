package com.music.feed.config

import com.music.feed.util.properties.MailProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.thymeleaf.TemplateEngine
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import org.thymeleaf.templateresolver.ITemplateResolver


@Configuration
class MailConfig {
    @Autowired
    lateinit var mailProperties: MailProperties

    @Value("\${spring.mail.port}")
    var port : Int = 25
    @Value("\${spring.mail.host}")
    lateinit var host : String
    @Value("\${spring.mail.protocol}")
    lateinit var protocol : String

    @Bean
    fun mailSender () : JavaMailSender{
        val mailSender = JavaMailSenderImpl()
        mailSender.host = host
        mailSender.port =  port
        mailSender.protocol = protocol
        mailSender.javaMailProperties = mailProperties.createProperties()

       return mailSender
    }

    private fun htmlTemplateResolver(): ITemplateResolver? {
        val templateResolver = ClassLoaderTemplateResolver()
        templateResolver.prefix = "/templates/html/"
        templateResolver.suffix = ".html"
        templateResolver.templateMode = TemplateMode.HTML
        templateResolver.characterEncoding = "UTF8"
        templateResolver.checkExistence = true
        templateResolver.isCacheable = false
        return templateResolver
    }

    @Bean(name = ["htmlTemplateEngine"])
    fun htmlTemplateEngine () : TemplateEngine{
        val templateEngine = TemplateEngine()
        templateEngine.addTemplateResolver(htmlTemplateResolver())
        return templateEngine
    }
}