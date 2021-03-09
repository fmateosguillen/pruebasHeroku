package g2Team1.apiRestKotlin.Modelos

import java.util.*
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Entity
class Vivienda (
                @get:NotBlank(message = "{vivienda.titulo.notBlank}")
                var titulo:String,

                var descripcion:String,

                @get:Min(message = "{vivienda.precio.Min}",value = 0)
                var precio:Float,

                var numHabitaciones:Int,

                var metrosCuadrados:Int,

                @get:NotBlank(message = "{vivienda.direccion.notBlank}")
                var direccion:String,

                @get:NotBlank(message = "{vivienda.localidad.notBlank}")
                var localidad:String,

                @get:NotBlank(message = "{vivienda.provincia.notBlank}")
                var provincia:String,
                var coordenadas:String,

                @get:NotBlank(message = "{vivienda.categoria.notBlank}")
                var categoria:String,
                @ManyToOne var propietario:User? = null,
                @ManyToMany(fetch = FetchType.EAGER)
                @JoinTable(name="MeGusta",
                    joinColumns = [JoinColumn(name = "vivienda_id")],
                    inverseJoinColumns = [JoinColumn(name = "user_id")]
                )
                var likeVivienda: MutableSet<User> = mutableSetOf<User>(),
                @OneToMany(mappedBy = "vivienda", fetch = FetchType.EAGER)
                var imagen: MutableSet<ImagenVivienda> = mutableSetOf<ImagenVivienda>(),
                @GeneratedValue @Id var id: UUID? = null

){
    override fun equals(other: Any?): Boolean {
        if(this===other) return true
        if(other==null || javaClass != other.javaClass ) return false
        val that=other as Vivienda
        if (id!=that.id) return false
        return true
    }

    override fun hashCode(): Int {
        return if (id!=null)
            id.hashCode()
        else 0
    }
    override fun toString(): String {
        return "Titulo $titulo, precio $precio, localidad $localidad , Likes ${likeVivienda.toString()}"
    }
    fun addImagen(imagen:ImagenVivienda){
        imagen.vivienda=this
        this.imagen.add(imagen)
    }
    fun deleteImagen(imagen:ImagenVivienda){
        imagen.vivienda=null
        this.imagen.remove(imagen)
    }
}

