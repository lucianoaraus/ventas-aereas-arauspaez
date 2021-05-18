package ar.edu.unahur.obj2.ventasAereas

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import java.lang.Exception


import java.time.LocalDate


class VueloTest : DescribeSpec({
    //Avion
    val avion1 = Avion(100,10)
    val avion2 = Avion(100,3)
    //Agregar aviones a Linea Aerea
    LineaAerea.agregarAviones(avion1)

    //Fechas de vuelos
    val fechaVuelo1 = LocalDate.of(2001, 9,11)
    val fechaVuelo2 = LocalDate.of(1941, 9,11)
    //val fechaVuelo3 = LocalDate.of(1941, 12,7)

    //Vuelos
    val vuelo1 = VueloPasajeros(fechaVuelo1, avion1,"Holanda","Panama",100.0)
    vuelo1.cambiarPolitica(Estricta)
    val vuelo2 = VueloCarga(fechaVuelo2, avion2,"Colombia","Los Angeles",100.0)
    vuelo2.cambiarPolitica(Estricta)
    //val vuelo3 = VueloCharter(fechaVuelo3, avion1,"Japon","Hawai",100.0, 10)
    //Pasajes
    val pasajePasajero = Pasaje(vuelo1,fechaVuelo1,42568689)


    //val pasajeCharter = Pasaje(vuelo3,fechaVuelo1,26587913)


    //Requerimiento 1
    describe("La cantidad de asientos libres...") {
        it("de un vuelo de pasajeros es:"){
            vuelo1.asientosLibres().shouldBe(100)
        }
    }

    //Requerimiento 2
    describe("Un vuelo de pasajeros...") {
        it("es relajado:"){
            vuelo1.esRelajado().shouldBeFalse()
        }
        it("No es relajado:"){
            vuelo2.esRelajado().shouldBeFalse()
        }
    }

    //Requerimiento 3
    describe("sePuedeVender...") {
        describe("Se pueden vender pasajes con criterio 'segura'") {
            LineaAerea.cambiarCriterio(Segura)
            it("para un vuelo de pasajeros:") {
                LineaAerea.sePuedeVender(pasajePasajero).shouldBeTrue()
            }
        }
        describe("No se pueden vender pasajes con criterio 'segura'") {
            it("para un vuelo de pasajeros:") {
                vuelo1.asientosOcupados = 98
                LineaAerea.sePuedeVender(pasajePasajero).shouldBeFalse()
            }
        }

        describe("Se pueden vender pasajes con criterio 'LaxaFija':") {
            LineaAerea.cambiarCriterio(LaxaFija)
            it("para un vuelo de pasajeros ") {
                vuelo1.asientosOcupados = 105
                LineaAerea.sePuedeVender(pasajePasajero).shouldBeTrue()
            }
        }
        describe("No se pueden vender pasajes con criterio 'LaxaFija':") {
            LineaAerea.cambiarCriterio(LaxaFija)
            it("para un vuelo de pasajeros ") {
                vuelo1.asientosOcupados = 110
                LineaAerea.sePuedeVender(pasajePasajero).shouldBeFalse()
            }
        }

        describe("Se pueden vender pasajes  con criterio 'LaxaPorcentual'") {
            LineaAerea.cambiarCriterio(LaxaPorcentual)
            it("para un vuelo de pasajeros:") {
                vuelo1.asientosOcupados = 105
                LineaAerea.sePuedeVender(pasajePasajero).shouldBeTrue()
            }
        }

        describe("No se pueden vender pasajes  con criterio 'LaxaPorcentual'") {
            LineaAerea.cambiarCriterio(LaxaPorcentual)
            it("para un vuelo de pasajeros:") {
                vuelo1.asientosOcupados = 110
                LineaAerea.sePuedeVender(pasajePasajero).shouldBeFalse()
            }
        }

        describe("No se pueden vender pasajes con criterio 'Pandemia'"){
            LineaAerea.cambiarCriterio(Pandemia)
            it("para un vuelo de pasajeros:") {
                LineaAerea.sePuedeVender(pasajePasajero).shouldBeFalse()
            }
        }
    }

    //Requerimiento 4
    describe("El precio de venta de un pasaje segun...") {
        it("politica Estricta:"){
            vuelo1.cambiarPolitica(Estricta)
            pasajePasajero.vuelo.preciosSegunPolitica().shouldBe(100)
        }
        it("politica Venta anticipada con menos de 40 asientos ocupados") {
            vuelo1.cambiarPolitica(VentaAnticipada)
            pasajePasajero.vuelo.preciosSegunPolitica().shouldBe(30)
        }
        it("politica Venta anticipada entre 40 y 79 asientos ocupados") {
            vuelo1.cambiarPolitica(VentaAnticipada)
            vuelo1.asientosOcupados = 50
            pasajePasajero.vuelo.preciosSegunPolitica().shouldBe(60)
        }
        it("politica Venta anticipada con mas de 79 asientos ocupados"){
            vuelo1.cambiarPolitica(VentaAnticipada)
            vuelo1.asientosOcupados = 80
            pasajePasajero.vuelo.preciosSegunPolitica().shouldBe(100)
        }
    }

    //Requerimiento 5
    describe("Registrar la venta de un pasaje para un vuelo, indicando fecha y DNI del comprador") {
        it("Se registra pasaje y se puede vender") {
            vuelo1.cambiarPolitica(Estricta)
            LineaAerea.cambiarCriterio(Segura)
            val pasajeVuelo = Pasaje(vuelo1,fechaVuelo1,38532223)
            LineaAerea.venderPasaje(pasajeVuelo)
            vuelo1.asientosOcupados.shouldBe(1)
            vuelo1.asientosDisponibles.shouldBe(99)
            vuelo1.cambiarPolitica(VentaAnticipada)
            pasajeVuelo.importe.shouldBe(100)
        }
        it("Se registra pasaje y no se puede vender por criterio"){
            vuelo1.cambiarPolitica(Estricta)
            val pasajeVuelo2 = Pasaje(vuelo1,fechaVuelo1,39532223)
            LineaAerea.cambiarCriterio(Pandemia)
            shouldThrowAny { LineaAerea.venderPasaje(pasajeVuelo2) }//tiene que tirar error
        }
    }
})
