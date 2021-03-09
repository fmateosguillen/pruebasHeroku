package g2Team1.apiRestKotlin.Upload

import g2Team1.apiRestKotlin.Modelos.ImagenVivienda
import g2Team1.apiRestKotlin.Modelos.Vivienda
import g2Team1.apiRestKotlin.Repositorios.ViviendaRepository
import g2Team1.apiRestKotlin.Servicios.base.BaseServiceImpl
import org.springframework.stereotype.Service
import java.util.*

@Service
class ServicioVivienda (): BaseServiceImpl<Vivienda,UUID,ViviendaRepository>()