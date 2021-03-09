package g2Team1.apiRestKotlin.Servicios

import g2Team1.apiRestKotlin.Modelos.ImagenVivienda
import g2Team1.apiRestKotlin.Repositorios.ImagenRepository
import g2Team1.apiRestKotlin.Servicios.base.BaseServiceImpl
import g2Team1.apiRestKotlin.Upload.ImgurImageAttribute
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*


@Service
class ImagenServicio(
    private val repo: ImagenRepository
) {
    fun save(ImagenVivienda: ImagenVivienda) = repo.save(ImagenVivienda)

    fun findAll() = repo.findAll()

    fun findById(id: UUID) = repo.findById(id)

    fun deleteById(id: UUID) = repo.deleteById(id)

    fun editById(id: UUID) = repo.save(findById(id).get())

}
@Service
class ImagenViviendaServicio(
    private val imageStorageService: ImgurStorageService

) : BaseServiceImpl<ImagenVivienda, UUID, ImagenRepository>() {

    val logger: org.slf4j.Logger? = LoggerFactory.getLogger(ImagenServicio::class.java)


    fun save(e: ImagenVivienda, file: MultipartFile) : ImagenVivienda {
        var imageAttribute : Optional<ImgurImageAttribute> = Optional.empty()
        if (!file.isEmpty) {
            imageAttribute = imageStorageService.store(file)
        }
        if (imageAttribute!=null){
            e.dataId = imageStorageService.loadAsResource(imageAttribute.get().id!!).get().uri.toString()
            e.deleteHash=imageAttribute.get().deletehash!!

        }
        save(e)
        return e
    }

    override fun delete(e : ImagenVivienda) {
        logger?.debug("Eliminando la entidad $e")
        e?.let { it.deleteHash?.let { it1 -> imageStorageService.delete(it1) } }
        super.delete(e)
    }



}