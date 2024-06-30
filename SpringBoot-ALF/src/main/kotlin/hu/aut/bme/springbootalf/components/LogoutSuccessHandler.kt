package hu.aut.bme.springbootalf.components

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler
import org.springframework.stereotype.Component

/**
 * Custom logout success handler to handle successful logout events.
 */
@Component
class LogoutSuccessHandler: SimpleUrlLogoutSuccessHandler() {

    // ObjectMapper to serialize JSON responses
    private val objectMapper = Jackson2ObjectMapperBuilder.json().build<ObjectMapper>()

    /**
     * Invoked after a successful logout by the LogoutFilter.
     *
     * @param request The HttpServletRequest
     * @param response The HttpServletResponse
     * @param authentication The Authentication object representing the user's authentication details.
     */
    override fun onLogoutSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication?) {
        // Create JSON response message
        val json = objectMapper.writeValueAsString("{\"message\":\"Successfully logged out\"}")

        // Set response headers
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"

        // Write JSON response to the output stream
        response.writer.write(json)
    }
}