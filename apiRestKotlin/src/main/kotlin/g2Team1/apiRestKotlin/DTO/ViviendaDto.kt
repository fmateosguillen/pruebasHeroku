package g2Team1.apiRestKotlin.DTO

import com.fasterxml.jackson.annotation.JsonInclude
import g2Team1.apiRestKotlin.Modelos.ImagenVivienda
import g2Team1.apiRestKotlin.Modelos.User
import g2Team1.apiRestKotlin.Modelos.Vivienda
import java.util.*


data class GetViviendaDto(
        val titulo:String,
        val precio:Float,
        val metrosCuadrados:Int,
        val numHabitaciones:Int,
        val localidad:String,
        val provincia:String,
        val categoria:String,
        val imagen: String?,
        val id: UUID?)
data class ViviendaCreate(
    val titulo:String,
    val precio:Float,
    val metrosCuadrados:Int,
    val numHabitaciones:Int,
    val localidad:String,
    val descripcion: String,
    val direccion: String,
    val coordenadas: String,
    val provincia:String,
    val categoria:String)

data class GetViviendaDtoUser(
    val titulo:String,
    val precio:Float,
    val metrosCuadrados:Int,
    val numHabitaciones:Int,
    val localidad:String,
    val provincia:String,
    val categoria:String,
    val imagen:String?,
    val meGusta:Boolean,
    val id: UUID?
)

    data class GetViviendaDetalleDto(

        var titulo:String,
        var descripcion:String,
        var precio:Float,
        var numHabitaciones:Int,
        var metrosCuadrados:Int,
        var direccion:String,
        var localidad:String,
        var provincia:String,
        var coordenadas:String,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var propietario: DtoPropiertario?,
        var imagen: MutableSet<DetalleImagenViviendaDto>,
        var categoria:String,
        var id: UUID?


    )
data class GetViviendaDetalleDtoUsuario(

    var titulo:String,
    var descripcion:String,
    var precio:Float,
    var numHabitaciones:Int,
    var metrosCuadrados:Int,
    var direccion:String,
    var localidad:String,
    var provincia:String,
    var coordenadas:String,
    var meGusta:Boolean,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var propietario: DtoPropiertario?,
    var imagen: MutableSet<DetalleImagenViviendaDto>,
    var categoria:String,
    var id: UUID?


)
    data class DtoPropiertario(
        val nombreCompleto:String?
    )
    fun Vivienda.toGetViviendaDto(user:User?,vivienda:Vivienda) : Any {
        return if (user!=null){
            GetViviendaDtoUser(titulo,precio, numHabitaciones,metrosCuadrados,localidad,provincia,categoria,if (imagen.size>0) imagen.first().dataId else null,user.likeUser.contains(vivienda),id)
        }else{
            GetViviendaDto(titulo,precio, numHabitaciones,metrosCuadrados,localidad,provincia,categoria,if (imagen.size>0) imagen.first().dataId else null,id)
        }

    }
    data class DetalleImagenViviendaDto(
        val id: UUID?,
        val url:String?,
        val deleteHash:String?
    )
    fun Vivienda.toGetViviendaDetalleDto(user: User?,vivienda: Vivienda) : Any {
        var imagenesVivienda: MutableSet<DetalleImagenViviendaDto> = mutableSetOf()
        if(vivienda.imagen.isNotEmpty()) {
            imagen.forEach {
                var imagenIterable: DetalleImagenViviendaDto = DetalleImagenViviendaDto(it.id, it.dataId, it.deleteHash)
                imagenesVivienda?.add(imagenIterable)
            }
        }
        return if (user==null){
            GetViviendaDetalleDto(titulo, descripcion, precio, numHabitaciones, metrosCuadrados, direccion,
                localidad, provincia, coordenadas, DtoPropiertario(vivienda.propietario?.fullName), imagenesVivienda, categoria, id)
        }else{
            GetViviendaDetalleDtoUsuario(titulo, descripcion, precio, numHabitaciones, metrosCuadrados, direccion,
                localidad, provincia, coordenadas, user.likeUser.contains(vivienda),DtoPropiertario(vivienda.propietario?.fullName), imagenesVivienda, categoria, id)
        }
    }




