package hu.aut.bme.springbootalf.components
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.Authentication
import java.io.PrintWriter
import java.io.StringWriter

@SpringBootTest
class LogoutSuccessHandlerTest {
    @InjectMocks
    private lateinit var logoutSuccessHandler: LogoutSuccessHandler
    @Mock
    private lateinit var request: HttpServletRequest
    @Mock
    private lateinit var response: HttpServletResponse
    @Mock
    private lateinit var authentication: Authentication

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun onLogoutSuccess() {
        val stringWriter = StringWriter()
        val writer = PrintWriter(stringWriter)
        `when`(response.writer).thenReturn(writer)

        logoutSuccessHandler.onLogoutSuccess(request, response, authentication)

        val responseBody = stringWriter.toString()
        val expectedResponse = "\"{\\\"message\\\":\\\"Successfully logged out\\\"}\""
        verify(response).contentType = "application/json"
        verify(response).characterEncoding = "UTF-8"
        assert(responseBody.contains(expectedResponse))
    }
}