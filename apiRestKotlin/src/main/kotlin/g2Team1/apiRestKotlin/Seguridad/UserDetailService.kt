package g2Team1.apiRestKotlin.Seguridad

import g2Team1.apiRestKotlin.Repositorios.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service("userDetailsService")
class UserDetailsServiceImpl() : UserDetailsService {
    @Autowired
    lateinit var userService: UserService

    override fun loadUserByUsername(username: String): UserDetails =
        userService.findByUsername(username).orElseThrow {
            UsernameNotFoundException("Usuario $username no encontrado")
        }
}