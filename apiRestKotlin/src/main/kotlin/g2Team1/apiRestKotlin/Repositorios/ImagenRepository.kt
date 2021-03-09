package g2Team1.apiRestKotlin.Repositorios

import g2Team1.apiRestKotlin.Modelos.ImagenVivienda
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ImagenRepository : JpaRepository<ImagenVivienda,UUID>