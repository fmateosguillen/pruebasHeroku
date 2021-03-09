package g2Team1.apiRestKotlin.Modelos

import java.util.*
import javax.persistence.AttributeConverter
import javax.persistence.Converter
import kotlin.collections.ArrayList


@Converter
class RolesConverter (): AttributeConverter<List<String>, String> {

    override fun convertToDatabaseColumn(list: List<String>?): String? {
        return java.lang.String.join(",", list)
    }
    override fun convertToEntityAttribute(joined: String): List<String>? = joined.split(",")


}