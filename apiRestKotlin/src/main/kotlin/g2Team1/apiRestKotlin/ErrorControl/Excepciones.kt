package g2Team1.apiRestKotlin.ErrorControl


import java.lang.RuntimeException
import java.util.*

open class EntityNotFoundException(val msg: String) : RuntimeException(msg)

data class SingleEntityNotFoundException(
    val id: UUID

) : EntityNotFoundException("No se puede encontrar la entidad con ID: ${id}")

data class ListEntityNotFoundException(val mensage: String= "No se pueden encontrar elementos del tipo  para esa consulta"
) : EntityNotFoundException(mensage)

