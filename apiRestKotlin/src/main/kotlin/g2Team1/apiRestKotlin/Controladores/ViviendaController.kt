package g2Team1.apiRestKotlin.Controladores

import g2Team1.apiRestKotlin.DTO.*
import g2Team1.apiRestKotlin.ErrorControl.EntityNotFoundException

import g2Team1.apiRestKotlin.ErrorControl.ListEntityNotFoundException
import g2Team1.apiRestKotlin.ErrorControl.SingleEntityNotFoundException
import g2Team1.apiRestKotlin.Modelos.User
import g2Team1.apiRestKotlin.Modelos.Vivienda
import g2Team1.apiRestKotlin.Repositorios.ViviendaRepository
import org.apache.logging.log4j.util.Strings.isNotEmpty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.server.ResponseStatusException
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/viviendas")
class ViviendaController {

    @Autowired
    lateinit var viviendaRepository: ViviendaRepository



    @GetMapping("")
    fun getAll(@AuthenticationPrincipal user:User?) : ResponseEntity<List<Any>> {
        var result= viviendaRepository.findAll()
            .map { it.toGetViviendaDto(user,it) }
            .takeIf { it.isNotEmpty() } ?:
        throw ListEntityNotFoundException()
        return ResponseEntity.ok().body(result)
    }



    @GetMapping("", params = arrayOf("cat","ciu","pre"))
    fun getAllfilter(@RequestParam("cat",defaultValue = "") cat:String,@RequestParam("ciu", defaultValue = "") ciu:String,@RequestParam("pre", defaultValue ="99999999" ) pre:String, @AuthenticationPrincipal user: User?): ResponseEntity<List<Any>> {
       var  result= viviendaRepository.findByCategoriaOrCiudadOrPrecio(cat,ciu,pre.toFloat()).map{ it.toGetViviendaDto(user,it)}
        if (result.isEmpty())
            throw ListEntityNotFoundException()
        return ResponseEntity.ok().body(result)

    }

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: UUID,@AuthenticationPrincipal user: User?) =
            viviendaRepository.findById(id)
                .map{it.toGetViviendaDetalleDto(user,it)}
                    .orElseThrow{
                        SingleEntityNotFoundException(id)
                    }

    @PostMapping
    fun create(@Valid @RequestBody vivienda : ViviendaCreate, @AuthenticationPrincipal user: User) : ResponseEntity<crearViviendaDto> =
            ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(viviendaRepository.save(Vivienda(
                            vivienda.titulo,
                            vivienda.descripcion,
                            vivienda.precio,
                            vivienda.numHabitaciones,
                            vivienda.metrosCuadrados,
                            vivienda.direccion,
                            vivienda.localidad,
                            vivienda.provincia,
                            vivienda.coordenadas,
                            vivienda.categoria,
                            user
                    )).toCreateDto(user))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id : UUID) : ResponseEntity<Any> {
        if(viviendaRepository.existsById(id))
            viviendaRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun edit(@Valid @PathVariable id: UUID,
             @RequestBody edited: Vivienda) : ResponseEntity<Vivienda> =
            viviendaRepository.findById(id)
                    .map { vivienda ->
                        vivienda.titulo = edited.titulo
                        vivienda.descripcion = edited.descripcion
                        vivienda.precio = edited.precio
                        vivienda.numHabitaciones = edited.numHabitaciones
                        vivienda.metrosCuadrados = edited.metrosCuadrados
                        vivienda.direccion = edited.direccion
                        vivienda.localidad = edited.localidad
                        vivienda.provincia = edited.provincia
                        vivienda.coordenadas = edited.coordenadas
                        vivienda.categoria = edited.categoria
                        ResponseEntity.ok(viviendaRepository.save(vivienda))
                    }.orElseThrow(){
                        SingleEntityNotFoundException(id)
                    }
    @GetMapping("/mine")
    fun MisViviendas(@AuthenticationPrincipal user:User) =user?.propiedades
        ?.map { it.toGetViviendaDto(user,it) }
}

