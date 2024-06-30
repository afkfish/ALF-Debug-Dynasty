package hu.aut.bme.springbootalf.components

import jakarta.mail.Message
import jakarta.mail.Session
import jakarta.mail.internet.MimeMessage
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mail.javamail.JavaMailSender
import java.io.ByteArrayInputStream

@SpringBootTest
class EmailServiceImplTest {

    @InjectMocks
    private lateinit var emailService: EmailServiceImpl

    @Mock
    private lateinit var emailSender: JavaMailSender

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun sendSimpleMessage() {
        val to = "test@example.com"
        val subject = "Test Subject"
        val text = "Test Text"
        val mimeMessage = MimeMessage(Session.getDefaultInstance(System.getProperties()), ByteArrayInputStream("test_email".toByteArray()))

        `when`(emailSender.createMimeMessage()).thenReturn(mimeMessage)

        emailService.sendSimpleMessage(to, subject, text)

        val argumentCaptor = ArgumentCaptor.forClass(MimeMessage::class.java)
        verify(emailSender).send(argumentCaptor.capture())
        val capturedMessage = argumentCaptor.value

        assertEquals(to, capturedMessage.getRecipients(Message.RecipientType.TO)[0].toString())
        assertEquals(subject, capturedMessage.subject)
    }
}