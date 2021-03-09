package g2Team1.apiRestKotlin.Controladores

import g2Team1.apiRestKotlin.DTO.toGetViviendaDto
import g2Team1.apiRestKotlin.ErrorControl.ListEntityNotFoundException
import g2Team1.apiRestKotlin.ErrorControl.SingleEntityNotFoundException
import g2Team1.apiRestKotlin.Modelos.User
import g2Team1.apiRestKotlin.Modelos.Vivienda
import g2Team1.apiRestKotlin.Repositorios.UserRepository
import g2Team1.apiRestKotlin.Repositorios.ViviendaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("viviendas/favs")
class FavoritosController (){

    @Autowired
    private lateinit var ViviendaRepositorio:ViviendaRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @PostMapping("/{id}")
    fun addFavorite(@PathVariable id:UUID, @AuthenticationPrincipal user: User) : ResponseEntity<Any>{
        val vivienda=ViviendaRepositorio.findById(id).orElseThrow { SingleEntityNotFoundException(id)}
        user.agregarFavoritos(vivienda)
        ViviendaRepositorio.save(vivienda)
        userRepository.save(user)
        return ResponseEntity.noContent().build()
    }
    @DeleteMapping("/{id}")
    fun eliminarFavorito(@PathVariable id: UUID, @AuthenticationPrincipal user:User): ResponseEntity<Any> {
        val vivienda=ViviendaRepositorio.findById(id).orElseThrow { SingleEntityNotFoundException(id) }
        user.eliminarFavoritos(vivienda)
        ViviendaRepositorio.save(vivienda)
        userRepository.save(user)
        return ResponseEntity.noContent().build()
    }
    @GetMapping("/")
    fun getAllFavorites(@AuthenticationPrincipal user:User) =user?.likeUser
            ?.map { it.toGetViviendaDto(user,it) }



}