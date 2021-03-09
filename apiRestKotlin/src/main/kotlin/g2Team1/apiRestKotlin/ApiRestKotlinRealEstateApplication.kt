package g2Team1.apiRestKotlin

import g2Team1.apiRestKotlin.Modelos.User
import g2Team1.apiRestKotlin.Modelos.Vivienda
import g2Team1.apiRestKotlin.Repositorios.UserRepository
import g2Team1.apiRestKotlin.Repositorios.ViviendaRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate

@SpringBootApplication
class ApiRestKotlinRealEstateApplication{
	/*@Bean
	fun init(usuRepo:UserRepository,passwordEncoder: PasswordEncoder, viviendaRepository: ViviendaRepository): CommandLineRunner {
		return CommandLineRunner {
			val user1= User("christianPP",passwordEncoder.encode("1234"),"Christian Payo Parra", LocalDate.of(2001,9,3), "christianpayo32@gmail.com",listOf("ADMIN","USER"))
			usuRepo.save(user1)

			val user2= User("pabloMC",passwordEncoder.encode("1234"),"Pablo Mancina Castro",LocalDate.of(2001,9,3), "pablomancina@gmail.com",listOf("ADMIN","USER"))
			usuRepo.save(user2)

			val v1= Vivienda("titulo","descripcion",35.0f,3,300,"micasa"
				,"Sevilla","Sevilla","2","Alquiler")
			viviendaRepository.save(v1)

			val v2= Vivienda("Pisito","Pisito en la Playa",500000.0f,4,300,"La playa"
				,"Málaga","Málaga","2","Alquiler")
			viviendaRepository.save(v2)

			val v3= Vivienda("Casoplon","casoplon",1000.0f,3,3000,"Andorra"
				,"Andorra","Andorra","2","Alquiler")
			viviendaRepository.save(v3)

			val v4= Vivienda("Zulo","agujero en el suelo",10.0f,1,3,"Sevilla"
				,"Sevilla","Sevilla","2","Venta")
			viviendaRepository.save(v4)

		}
	}*/
}

fun main(args: Array<String>) {
	runApplication<ApiRestKotlinRealEstateApplication>(*args)
}
