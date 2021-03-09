package g2Team1.apiRestKotlin.Modelos

import net.bytebuddy.build.ToStringPlugin
import java.util.*
import javax.persistence.*

@Entity
class ImagenVivienda (
    var dataId:String? =null,
    var deleteHash:String? = null,
    @ManyToOne
    var vivienda: Vivienda? = null,
    @Id @GeneratedValue var id: UUID?=null){

}