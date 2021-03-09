package g2Team1.apiRestKotlin.DTO

import g2Team1.apiRestKotlin.Modelos.ImagenVivienda
import java.util.*

data class ImagenViviendaDTO(

    var url : String,
    var deleteHash : String,
    val id: UUID
)

data class DtoImagenesInfo(

    var url:String,
    var deleteHash:String,
    var id : UUID
)

fun ImagenVivienda.toGetImagenViviendaInfo():DtoImagenesInfo{
    return DtoImagenesInfo( dataId!!, deleteHash!! ,id!!)

}

fun ImagenVivienda.toGetDtoImagenVivienda():ImagenViviendaDTO{
    return ImagenViviendaDTO(dataId!! , deleteHash!!,id!!)}



fun ImagenVivienda.toDto() = ImagenViviendaDTO( dataId!!, deleteHash!!,id!! )