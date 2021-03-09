package g2Team1.apiRestKotlin.Repositorios

import g2Team1.apiRestKotlin.DTO.CreateUserDTO
import g2Team1.apiRestKotlin.Modelos.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

interface UserRepository : JpaRepository<User,UUID>{
    fun findByUsername(username : String) : Optional<User>
}

@Service
class UserService() {
    @Autowired
    lateinit var repo: UserRepository
    @Autowired
    lateinit var encoder: PasswordEncoder

    fun create(newUser : CreateUserDTO): Optional<User> {
        if (findByUsername(newUser.username).isPresent)
            return Optional.empty()
        return Optional.of(
            with(newUser) {
                repo.save(User(username, encoder.encode(password), fullName, LocalDate.parse(newUser.fechaNacimiento,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")), email, listOf("USER")))
            }

        )
    }

    fun findByUsername(username : String) = repo.findByUsername(username)

    fun findById(id : UUID) = repo.findById(id)

}