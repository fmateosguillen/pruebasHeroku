package g2Team1.apiRestKotlin.Modelos

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.*


@Entity
class User(
    @get:NotBlank(message = "{user.username.notBlank}")
    @get:Size(message = "{user.username.min}",min = 3)
    @Column(nullable = false, unique = true)
    private var username: String,

    @get:NotBlank(message = "{user.password.notBlank}")
    @get:Size(message = "{user.password.min}",min = 5)
    private var password: String,
    @get:NotBlank(message = "{user.fullname.notBlank}")
    var fullName : String,
    @get:NotNull(message = "{user.nacimiento.notNull}")
    @get:Past(message = "{user.nacimiento.anterior}")
    var fechaNacimiento:LocalDate,
    var activo:Boolean,
    @get:NotBlank(message = "{user.email.notBlank}")
    @get:Email(message = "{user.email.valid}")
    var email: String,
    @Column
    @Convert(converter = RolesConverter::class)
    var roles: List<String>,

    private val nonExpired: Boolean = true,

    private val nonLocked: Boolean = true,

    private val enabled: Boolean = true,
    private val credentialsNonExpired : Boolean = true,
    var fechaAlta:LocalDate= LocalDate.now(),

    @OneToMany(mappedBy = "propietario", fetch = FetchType.EAGER) var propiedades: MutableSet<Vivienda> = mutableSetOf<Vivienda>(),

    @ManyToMany(mappedBy = "likeVivienda",fetch = FetchType.EAGER) var likeUser: MutableSet<Vivienda> = mutableSetOf<Vivienda>(),


    @Id @GeneratedValue val id : UUID? = null


) : UserDetails{
    constructor(username: String, password: String, fullName: String,fechaNacimiento: LocalDate,email: String, role: List<String>) :
            this(username, password, fullName,fechaNacimiento,true,email, role, true, true, true)

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? =roles.map { SimpleGrantedAuthority("ROLE_$it") }.toMutableList()

    override fun isEnabled() = enabled
    override fun getUsername() = username
    override fun isCredentialsNonExpired() = credentialsNonExpired
    override fun getPassword() = password
    override fun isAccountNonExpired() = nonExpired
    override fun isAccountNonLocked() = nonLocked


    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other === null || other !is User)
            return false
        if (this::class != other::class)
            return false
        return id == other.id
    }

    override fun hashCode(): Int {
        if (id == null)
            return super.hashCode()
        return id.hashCode()
    }

    override fun toString(): String {
        return "Username $username , Fullname $fullName , fechaNacimiento $fechaNacimiento , fechaAlta $fechaAlta , roles $roles"
    }

    fun agregarFavoritos(vivienda: Vivienda){
        vivienda.likeVivienda.add(this)
        likeUser.add(vivienda)
    }
    fun eliminarFavoritos(vivienda: Vivienda){
        vivienda.likeVivienda.remove(this)
        likeUser.remove(vivienda)
    }
}
