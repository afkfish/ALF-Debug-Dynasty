package hu.aut.bme.springbootalf.components

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.stereotype.Component

/**
 * Custom authentication entry point for REST APIs.
 */
@Component
class RestAuthenticationEntryPoint: BasicAuthenticationEntryPoint() {

    // ObjectMapper to serialize JSON responses
    private val objectMapper = Jackson2ObjectMapperBuilder.json().build<ObjectMapper>()

    /**
     * Invoked when authentication fails and an unauthorized response should be sent.
     *
     * @param request The HttpServletRequest (not used)
     * @param response The HttpServletResponse to send the unauthorized response
     * @param authEx The AuthenticationException that caused the authentication failure
     */
    override fun commence(request: HttpServletRequest?, response: HttpServletResponse, authEx: AuthenticationException?) {
        // Add authentication realm to the response header
        val authHeader = String.format("Auth realm=\"%s\"", realmName)
        response.addHeader("WWW-Authenticate", authHeader)

        // Set HTTP status code to 401 (Unauthorized)
        response.status = HttpServletResponse.SC_UNAUTHORIZED

        // Create and send JSON response
        val json = objectMapper.writeValueAsString("{\"error\":\"unauthorized\"}")
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer.write(json)
    }

    /**
     * Sets the realm name for the authentication entry point.
     * This method is invoked after properties are set.
     */
    override fun afterPropertiesSet() {
        realmName = "hu.bme.aut"
        super.afterPropertiesSet()
    }
}