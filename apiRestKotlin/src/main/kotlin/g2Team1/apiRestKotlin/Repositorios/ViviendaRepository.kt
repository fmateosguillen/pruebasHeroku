package g2Team1.apiRestKotlin.Repositorios

import g2Team1.apiRestKotlin.Modelos.Vivienda
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ViviendaRepository : JpaRepository<Vivienda,UUID>{

    @Query("Select f from Vivienda f Where f.categoria = :CATEGORIA and f.localidad= :CIUDAD and f.precio<= :PRECIO")
    fun findByArgs(@Param("CATEGORIA") category:String,@Param("CIUDAD") ciudad:String, @Param("PRECIO") precio:Float):List<Vivienda>


    @Query("Select f from Vivienda f Where  f.localidad= :CIUDAD and f.precio<= :PRECIO")
    fun findByCiudadAndPrecio(@Param("CIUDAD") ciudad:String, @Param("PRECIO") precio:Float):List<Vivienda>

    @Query("Select f from Vivienda f Where f.categoria = :CATEGORIA  and f.precio<= :PRECIO")
    fun findByCategoriaAndPrecio(@Param("CATEGORIA") category:String, @Param("PRECIO") precio:Float):List<Vivienda>

    @Query("Select f from Vivienda f Where  f.precio<= :PRECIO")
    fun findByPrecio( @Param("PRECIO") precio:Float):List<Vivienda>


    @Query("select v from Vivienda v where lower(v.categoria) like lower(concat('%',:cat,'%')) and lower(v.localidad) like lower(concat('%',:ciu,'%')) and v.precio <= (:pre)")
    fun findByCategoriaOrCiudadOrPrecio(
        @Param("cat") cat: String,
        @Param("ciu") ciu: String,
        @Param("pre") pre: Float,
    ):List<Vivienda>
}



