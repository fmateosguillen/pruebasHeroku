package g2Team1.apiRestKotlin.Controladores

import g2Team1.apiRestKotlin.DTO.ImagenViviendaDTO
import g2Team1.apiRestKotlin.DTO.toDto
import g2Team1.apiRestKotlin.Modelos.ImagenVivienda
import g2Team1.apiRestKotlin.Servicios.ImagenViviendaServicio
import g2Team1.apiRestKotlin.Servicios.ImgurStorageService
import g2Team1.apiRestKotlin.Upload.ServicioVivienda
import g2Team1.apiRestKotlin.Upload.ImgurBadRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/viviendas")
class ImagenViviendaController(
    private val servicioVivienda: ServicioVivienda,
    private val servicio: ImagenViviendaServicio,
    val imgurStorageService: ImgurStorageService
) {

    @PostMapping("/{id}/img")
    fun create(@PathVariable id: UUID, @RequestPart("file") file: MultipartFile) : ResponseEntity<ImagenViviendaDTO> {

        try {
            val new = ImagenVivienda()

            var vivienda=servicioVivienda.findById(id)
            var guardado= servicio.save( new ,file)
            vivienda.get().addImagen(guardado)
            servicioVivienda.save(vivienda.get())


            return ResponseEntity.status(HttpStatus.CREATED).body(guardado.toDto())
        } catch ( ex : ImgurBadRequest) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Error en la subida de la imagen")
        }

    }

    @DeleteMapping("/{id}/img/{hash}")
    fun delete(@PathVariable hash: String,@PathVariable id: UUID):ResponseEntity<Any> {

        var vivienda= servicioVivienda.findById(id)
        if (vivienda!=null){
            vivienda.get().imagen.forEach {
                if (hash==it.deleteHash){
                    vivienda.get().deleteImagen(it)
                    servicioVivienda.save(vivienda.get())
                    servicio.delete(it)
                    imgurStorageService.delete(hash)

                }
            }
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()

    }

}