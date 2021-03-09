package g2Team1.apiRestKotlin.Seguridad.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import g2Team1.apiRestKotlin.Modelos.ApiError
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationEntryPoint(
    val mapper : ObjectMapper
)  : AuthenticationEntryPoint {


    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        response?.status = 401
        response?.contentType = "application/json"
        response?.writer?.println(mapper.writeValueAsString(authException?.message?.let { ApiError(HttpStatus.UNAUTHORIZED,"Acceso denegado ${authException.message}") }))
    }

}
