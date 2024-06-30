package hu.aut.bme.springbootalf.components

import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.AuthenticationException

@SpringBootTest
class RestAuthenticationEntryPointTest {

    private lateinit var restAuthenticationEntryPoint: RestAuthenticationEntryPoint
    private lateinit var request: MockHttpServletRequest
    private lateinit var response: MockHttpServletResponse
    private lateinit var authEx: AuthenticationException

    @BeforeEach
    fun setUp() {
        restAuthenticationEntryPoint = RestAuthenticationEntryPoint()
        request = MockHttpServletRequest()
        response = MockHttpServletResponse()
        authEx = AuthenticationServiceException("Test Exception")
    }

    @Test
    fun commence() {
        restAuthenticationEntryPoint.afterPropertiesSet()
        restAuthenticationEntryPoint.commence(request, response, authEx)

        assertEquals("Auth realm=\"hu.bme.aut\"", response.getHeader("WWW-Authenticate"))
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.status)
        assertEquals("application/json;charset=UTF-8", response.contentType)
        assertEquals("UTF-8", response.characterEncoding)
    }

    @Test
    fun afterPropertiesSet() {
        restAuthenticationEntryPoint.afterPropertiesSet()

        assertEquals("hu.bme.aut", restAuthenticationEntryPoint.realmName)
    }
}