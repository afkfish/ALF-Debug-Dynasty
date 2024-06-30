package hu.aut.bme.springbootalf.components

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component

/**
 * Service class for sending emails using Spring Mail.
 */
@Component
class EmailServiceImpl {

    @Autowired
    lateinit var emailSender: JavaMailSender

    /**
     * Sends a simple email message.
     *
     * @param to The recipient email address.
     * @param subject The subject of the email.
     * @param text The body of the email.
     */
    fun sendSimpleMessage(
        to: String,
        subject: String,
        text: String
    ) {
        // Create a MimeMessage instance
        val message = emailSender.createMimeMessage()

        // Create MimeMessageHelper instance for easier email construction
        val helper = MimeMessageHelper(message, true)

        // Set sender email address
        helper.setFrom("debug.dynasy@gmail.com")

        // Set recipient email address
        helper.setTo(to)

        // Set email subject
        helper.setSubject(subject)

        // Set email body
        helper.setText(text)

        // Send the email message
        return emailSender.send(message)
    }
}