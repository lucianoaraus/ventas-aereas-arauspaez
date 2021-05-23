package ar.edu.unahur.obj2.ventasAereas


import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import java.time.LocalDate


class VueloTest : DescribeSpec({

    //Avion
    val avion1 = Avion(100,10, 25000.0)
    val avion2 = Avion(100,3, 15000.0)
    val avion3 = Avion(100,6, 10000.0)

    //Agregar aviones a Linea Aerea y cambiar criterio inicial
    LineaAerea.agregarAviones(avion1)
    LineaAerea.agregarAviones(avion2)
    LineaAerea.agregarAviones(avion3)
    LineaAerea.cambiarCriterio(Segura)

    //Fechas de vuelos
    val fechaVuelo1 = LocalDate.of(2001, 9,11)
    val fechaVuelo2 = LocalDate.of(1941, 9,11)
    val fechaVuelo3 = LocalDate.of(2004, 12,25)

    //Vuelos
    val vuelo1 = VueloPasajeros(fechaVuelo1, avion1,"Holanda","Panama",100.0)
    vuelo1.cambiarPolitica(Estricta)
    val vuelo2 = VueloCarga(fechaVuelo2, avion2,"Colombia","Panama",100.0, 2000.0)
    vuelo2.cambiarPolitica(Estricta)
    val vuelo3 = VueloCharter(fechaVuelo3, avion3,"Japon","Hawai",100.0, 0)
    vuelo3.cambiarPolitica(Estricta)

    //Agregar vuelos a la LineaAerea
    LineaAerea.vuelos = mutableListOf(vuelo1,vuelo2,vuelo3)

    //Pasajes y fecha de venta
    val fechaVenta1 = LocalDate.of(2000, 12,31)
    val pasajePasajero = Pasaje(vuelo1,fechaVenta1,42568689)
    val pasajePasajero1 = Pasaje(vuelo1,fechaVenta1,42568687)
    val pasajePasajero2 = Pasaje(vuelo1,fechaVenta1,42568682)
    val pasajePasajero3 = Pasaje(vuelo1,fechaVenta1,42568682)
    val pasajeVuelo = Pasaje(vuelo1,fechaVenta1,38532223)

    //Requerimiento 8 y 10
    val pasajero1pasaje1 = Pasaje(vuelo1,fechaVenta1,40500600)
    val pasajero1pasaje2 = Pasaje(vuelo2,fechaVenta1,40500600)
    val pasajero1pasaje3 = Pasaje(vuelo3,fechaVenta1,40500600)

    val pasajero2pasaje1 = Pasaje(vuelo1,fechaVenta1,30500600)
    val pasajero2pasaje2 = Pasaje(vuelo2,fechaVenta1,30500600)
    val pasajero2pasaje3 = Pasaje(vuelo3,fechaVenta1,30500600)

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
        it("politica Estricta"){
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
        it("politica Remate con mas de 30 asientos libres"){
            vuelo1.cambiarPolitica(Remate)
            vuelo1.asientosOcupados = 40
            pasajePasajero.vuelo.preciosSegunPolitica().shouldBe(25.0)
        }
        it("politica Remate con menos de 30 asientos libres"){
            vuelo1.cambiarPolitica(Remate)
            vuelo1.asientosOcupados = 80
            pasajePasajero.vuelo.preciosSegunPolitica().shouldBe(50.0)
        }
    }

    //Requerimiento 5
    describe("Registrar la venta de un pasaje para un vuelo, indicando fecha y DNI del comprador") {
        it("Se registra pasaje y se puede vender") {
            vuelo1.cambiarPolitica(Estricta)
            LineaAerea.venderPasaje(pasajeVuelo)
            vuelo1.asientosOcupados.shouldBe(1)
            vuelo1.asientosDisponibles.shouldBe(99)
            vuelo1.cambiarPolitica(VentaAnticipada)
            pasajeVuelo.importe.shouldBe(100)
        }
        it("Se registra pasaje y no se puede vender por criterio"){
            vuelo1.cambiarPolitica(Estricta)
            LineaAerea.cambiarCriterio(Pandemia)
            shouldThrowAny { LineaAerea.venderPasaje(pasajeVuelo) }
        }
    }

    //Requerimiento 6
    describe("Saber, para un vuelo, el importe total generado por venta de pasajes."){
        it("importeTotal para un vuelo con 3 pasajes vendidos"){
            LineaAerea.venderPasaje(pasajePasajero1)
            LineaAerea.venderPasaje(pasajePasajero2)
            LineaAerea.venderPasaje(pasajePasajero3)
            vuelo1.importeTotal.shouldBe(300.0)
        }
    }

    //Requerimiento 7
    describe("Saber el peso máximo de un vuelo"){
        IATA.pesoEstandar = 80.0 // Peso estandar de cada pasajero
        it("Vuelo de pasajeros"){
            vuelo1.asientosOcupados = 50
            vuelo1.pesoMaximo().shouldBe(39000.0)
        }
        it("Vuelo de carga"){
            vuelo2.asientosOcupados = 5
            vuelo2.pesoMaximo().shouldBe(18100.0)
        }
        it("Vuelo charter"){
            vuelo3.asientosOcupados = 25
            vuelo3.pesoMaximo().shouldBe(17000.0)
        }
    }

    //Requerimiento 8
    describe("Saber para que fechas una persona tiene sacados pasajes para un determinado destino"){
        it("El 2001/9/11 la persona con DNI 40500600 tiene vuelo con destino a Panama"){
            vuelo1.pasajes = mutableListOf(pasajero1pasaje1)
            vuelo2.pasajes = mutableListOf(pasajero1pasaje2)
            vuelo3.pasajes = mutableListOf(pasajero1pasaje3)
            LineaAerea.fechasConPasajeSegunDNI(40500600,"Panama").shouldContainExactly(fechaVuelo1,fechaVuelo2)
        }
    }

    //Requerimiento 9
    describe("Conocer el total de asientos libres para un destino entre dos fechas"){
        it("total de asientos libres entre 1940/1/1 y 2002/1/1"){
            val primerFecha = LocalDate.of(1940, 1,1)
            val segundaFecha = LocalDate.of(2002,1,1)
            LineaAerea.totalAsientosLibres("Panama",primerFecha,segundaFecha).shouldBe(110)
        }
    }

    //Requerimiento 10
    describe("Conocer si dos personas son compañeras"){
        vuelo1.pasajes = mutableListOf(pasajero1pasaje1,pasajero2pasaje1,pasajePasajero)
        vuelo2.pasajes = mutableListOf(pasajero1pasaje2,pasajero2pasaje2)
        vuelo3.pasajes = mutableListOf(pasajero1pasaje3,pasajero2pasaje3)
        it("comparten al menos 3 vuelos"){
            LineaAerea.personasCompanieras(40500600,30500600).shouldBeTrue()
        }
        it("NO comparten al menos 3 vuelos"){
            LineaAerea.personasCompanieras(40500600,42568689).shouldBeFalse()
        }
    }
})

