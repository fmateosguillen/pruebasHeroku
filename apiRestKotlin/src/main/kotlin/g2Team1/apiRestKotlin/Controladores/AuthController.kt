package g2Team1.apiRestKotlin.Controladores

import g2Team1.apiRestKotlin.DTO.*
import g2Team1.apiRestKotlin.Modelos.User
import g2Team1.apiRestKotlin.Repositorios.UserService
import g2Team1.apiRestKotlin.Seguridad.jwt.BearerTokenExtractor
import g2Team1.apiRestKotlin.Seguridad.jwt.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
class AuthenticationController() {
    @Autowired
    lateinit var authenticationManager: AuthenticationManager
    @Autowired
    lateinit var jwtTokenProvider: JwtTokenProvider
    @Autowired
    lateinit var bearerTokenExtractor: BearerTokenExtractor
    @Autowired
    lateinit var userService: UserService

    @PostMapping("/auth/login")
    fun login(@Valid @RequestBody loginRequest : LoginRequest) : ResponseEntity<JwtUserResponseLogin> {
        val authentication = do_authenticate(loginRequest.username,loginRequest.password)

        SecurityContextHolder.getContext().authentication = authentication

        val user = authentication.principal as User

        val jwtToken = jwtTokenProvider.generateToken(user)
        val jwtRefreshToken = jwtTokenProvider.generateRefreshToken(user)

        return ResponseEntity.status(HttpStatus.CREATED).body(JwtUserResponseLogin(jwtToken, jwtRefreshToken, user.UserDTOLogin()))

    }
    @PostMapping("/auth/token")
    fun refreshToken(request : HttpServletRequest) : ResponseEntity<JwtUserResponseLogin> {

        val refreshToken = bearerTokenExtractor.getJwtFromRequest(request).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al procesar el token de refresco")
        }

        try {
            if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
                val userId = jwtTokenProvider.getUserIdFromJWT(refreshToken)
                val user: User = userService.findById(userId).orElseThrow {
                    UsernameNotFoundException("No se ha podido encontrar el usuario a partir de su ID")
                }
                val jwtToken = jwtTokenProvider.generateToken(user)
                val jwtRefreshToken = jwtTokenProvider.generateRefreshToken(user)

                return ResponseEntity.status(HttpStatus.CREATED).body(JwtUserResponseLogin(jwtToken, jwtRefreshToken, user.UserDTOLogin()))
            }
        } catch (ex : Exception) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Error en la validación del token")
        }

        return ResponseEntity.badRequest().build()

    }

    @GetMapping("/auth/logout")
    fun logout() : ResponseEntity<Any> {
        SecurityContextHolder.clearContext()
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/auth/register")
    fun nuevoUsuario(@Valid @RequestBody newUser : CreateUserDTO): ResponseEntity<JWTUserResponseRegister> {
        var user=userService.create(newUser).orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de usuario ${newUser.username} ya existe") }
        val authentication=do_authenticate(newUser.username,newUser.password)
        SecurityContextHolder.getContext().authentication = authentication
        val user1 = authentication.principal as User
        val jwtToken = jwtTokenProvider.generateToken(user1)
        val jwtRefreshToken = jwtTokenProvider.generateRefreshToken(user1)
        return ResponseEntity.status(HttpStatus.CREATED).body(JWTUserResponseRegister(jwtToken, jwtRefreshToken, user.UserDTORegister()))
    }


    private fun do_authenticate(username: String,password: String): Authentication {
        return  authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                username, password
            )
        )
    }
}


data class LoginRequest(
    @get:NotBlank(message = "{user.username.notBlank}") val username : String,
    @get:NotBlank(message = "{user.password.notBlank}") val password: String
)

data class JwtUserResponseLogin(
    val token: String,
    val refreshToken: String,
    val user: UserDTOlogin
)

data class JWTUserResponseRegister(
    val token: String,
    val refreshToken: String,
    val user:UserDTORegisterModel)
