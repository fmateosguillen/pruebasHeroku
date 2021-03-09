package g2Team1.apiRestKotlin.DTO

import com.fasterxml.jackson.annotation.JsonClassDescription
import com.fasterxml.jackson.annotation.JsonFormat
import g2Team1.apiRestKotlin.Modelos.User
import g2Team1.apiRestKotlin.Modelos.Vivienda
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.*

data class UserDTOlogin(
    var username : String,
    var email: String,
    var nombre: String,
    val id: UUID? = null
)
data class UserDTORegisterModel(
    val username: String,
    val nombre: String,
    val fechaNacimiento: LocalDate,
    val id: UUID? = null
)
fun User.UserDTOLogin() = UserDTOlogin(username, email,fullName, id)

fun User.UserDTORegister() = UserDTORegisterModel(username,fullName,fechaNacimiento,id)

data class CreateUserDTO(
    @get:NotBlank(message = "{user.username.notBlank}")
    @get:Size(message = "{user.username.min}",min = 3)
    var username: String,
    @get:NotBlank(message = "{user.fullname.notBlank}")
    var fullName: String,
    @get:NotBlank(message = "{user.email.notBlank}")
    @get:Email(message = "{user.email.valid}")
    var email:String,
    @get:NotBlank(message = "{user.password.notBlank}")
    @get:Size(message = "{user.password.min}",min = 5)
    val password: String,
    @get:NotBlank(message = "{user.password.notBlank}")
    @get:Size(message = "{user.password.min}",min = 5)
    val password2: String,
    @get:NotNull(message = "{user.nacimiento.notNull}")
    var fechaNacimiento: String
)

data class crearViviendaDto(
    val id: UUID?,
    val titulo:String,
    val description: String,
    val precio:Float,
    val nHab:Int,
    val metros:Int,
    val direccion:String,
    val localidad:String,
    val provincia:String,
    val coordenadas:String,
    val categoria:String,
    val propiertario: DtoPropiertario,
    val imagen:String?
)
fun Vivienda.toCreateDto(user:User): crearViviendaDto {
    return crearViviendaDto(id,titulo,descripcion,precio,numHabitaciones,metrosCuadrados
        ,direccion,localidad,provincia,coordenadas,categoria,DtoPropiertario(user.fullName),if (imagen.size!=0) imagen.first().dataId else null)
}