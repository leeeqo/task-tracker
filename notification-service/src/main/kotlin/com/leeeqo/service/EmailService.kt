package com.leeeqo.service

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

private val kLogger = KotlinLogging.logger {}

@Service
class EmailService(
    @Value("\${spring.mail.username}")
    private val appName: String,

    private val mailSender: JavaMailSender
) {

    fun send(recipient: String, sbj: String, txt: String) = with(SimpleMailMessage()) {
        setTo(recipient)
        subject = sbj
        text = txt
        from = appName

        mailSender.send(this)
    }.also {
        kLogger.info { "NOTIFICATION: message was sent by Email" }
    }
}
