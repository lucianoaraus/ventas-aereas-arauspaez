import ar.edu.unahur.obj2.ventasAereas.*
import io.kotest.matchers.shouldBe
import java.time.LocalDate

// Pueden usar este archivo para hacer pruebas rápidas,
// de la misma forma en que usaban el REPL de Wollok.

// OJO: lo que esté aquí no será tenido en cuenta
// en la corrección ni reemplaza a los tests.

val avion1 = Avion(100,10)

//Agregar aviones a Linea Aerea
LineaAerea.agregarAviones(avion1)

//Vuelos
val fechaVuelo1 = LocalDate.of(2001, 9,11)
val vuelo1 = VueloPasajeros(fechaVuelo1, avion1,"Holanda","Panama",100.0)
val vuelo2 = VueloCarga(fechaVuelo1, avion1,"Noruega","Ecuador",100.0)
val vuelo3 = VueloCharter(fechaVuelo1, avion1,"Eslovaquia","Thailandia",100.0, 10)

//Pasajes
val pasajePasajero = Pasaje(vuelo1,fechaVuelo1,42568689)
val pasajeCarga = Pasaje(vuelo2,fechaVuelo1,42568689)
val pasajeCharter = Pasaje(vuelo3,fechaVuelo1,42568689)

LineaAerea.cambiarCriterio(Segura)
LineaAerea.sePuedeVender(pasajePasajero)
LineaAerea.sePuedeVender(pasajeCarga)
LineaAerea.sePuedeVender(pasajeCharter)

LineaAerea.cambiarCriterio(LaxaFija)
LineaAerea.sePuedeVender(pasajePasajero)
LineaAerea.sePuedeVender(pasajeCarga)
LineaAerea.sePuedeVender(pasajeCharter)

LineaAerea.cambiarCriterio(LaxaPorcentual)
LineaAerea.sePuedeVender(pasajePasajero)
LineaAerea.sePuedeVender(pasajeCarga)
LineaAerea.sePuedeVender(pasajeCharter)

LineaAerea.cambiarCriterio(Pandemia)
LineaAerea.sePuedeVender(pasajePasajero)
LineaAerea.sePuedeVender(pasajeCarga)
LineaAerea.sePuedeVender(pasajeCharter)



LineaAerea.cambiarCriterio(Segura)
vuelo1.asientosOcupados = 98
LineaAerea.sePuedeVender(pasajePasajero)
vuelo2.asientosOcupados = 8
LineaAerea.sePuedeVender(pasajeCarga)
vuelo3.asientosOcupados = 73
LineaAerea.sePuedeVender(pasajeCharter)

LineaAerea.cambiarCriterio(LaxaFija)
vuelo1.asientosOcupados = 105
LineaAerea.sePuedeVender(pasajePasajero)
vuelo2.asientosOcupados = 15
LineaAerea.sePuedeVender(pasajeCarga)
vuelo3.asientosOcupados = 80
LineaAerea.sePuedeVender(pasajeCharter)

LineaAerea.cambiarCriterio(LaxaFija)
vuelo1.asientosOcupados = 110
LineaAerea.sePuedeVender(pasajePasajero)
vuelo2.asientosOcupados = 20
LineaAerea.sePuedeVender(pasajeCarga)
vuelo3.asientosOcupados = 85
LineaAerea.sePuedeVender(pasajeCharter)

LineaAerea.cambiarCriterio(LaxaPorcentual)
vuelo1.asientosOcupados = 105
LineaAerea.sePuedeVender(pasajePasajero)
vuelo2.asientosOcupados = 10
LineaAerea.sePuedeVender(pasajeCarga)
vuelo3.asientosOcupados = 80
LineaAerea.sePuedeVender(pasajeCharter)

LineaAerea.cambiarCriterio(LaxaPorcentual)
vuelo1.asientosOcupados = 110
LineaAerea.sePuedeVender(pasajePasajero)
vuelo2.asientosOcupados = 11
LineaAerea.sePuedeVender(pasajeCarga)
vuelo3.asientosOcupados = 82
LineaAerea.sePuedeVender(pasajeCharter)

LineaAerea.cambiarCriterio(Pandemia)
LineaAerea.sePuedeVender(pasajePasajero)
LineaAerea.sePuedeVender(pasajeCarga)
LineaAerea.sePuedeVender(pasajeCharter)


