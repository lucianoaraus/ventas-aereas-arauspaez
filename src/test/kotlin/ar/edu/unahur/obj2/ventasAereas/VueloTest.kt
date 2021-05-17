package ar.edu.unahur.obj2.ventasAereas

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

import java.time.LocalDate

class VueloTest : DescribeSpec({
  //Avion
  val avion1 = Avion(100,10)

  //Agregar aviones a Linea Aerea
  LineaAerea.agregarAviones(avion1)

  //Fechas de vuelos
  val fechaVuelo1 = LocalDate.of(2001, 9,11)
  val fechaVuelo2 = LocalDate.of(1941, 9,11)
  val fechaVuelo3 = LocalDate.of(1941, 12,7)

  //Vuelos
  val vuelo1 = VueloPasajeros(fechaVuelo1, avion1,"Holanda","Panama",100.0)
  val vuelo2 = VueloCarga(fechaVuelo2, avion1,"Colombia","Los Angeles",100.0)
  val vuelo3 = VueloCharter(fechaVuelo3, avion1,"Japon","Hawai",100.0, 10)

  //Pasajes
  val pasajePasajero = Pasaje(vuelo1,fechaVuelo1,42568689)
  val pasajeCarga = Pasaje(vuelo2,fechaVuelo1,38597286)
  val pasajeCharter = Pasaje(vuelo3,fechaVuelo1,26587913)


  //Requerimiento 1
  describe("La cantidad de asientos libres...") {
    it("de un vuelo de pasajeros es:"){
      vuelo1.asientosLibres().shouldBe(100)
    }
    it("de un vuelo de carga es:"){
      vuelo2.asientosLibres().shouldBe(10)
    }
    it("de un vuelo de charter es:"){
      vuelo3.asientosLibres().shouldBe(65)
    }
  }

  //Requerimiento 2
  describe("Es relajado...") {
    it("un vuelo de pasajeros:"){
      vuelo1.esRelajado().shouldBeFalse()
    }
    it("un vuelo de carga:"){
      vuelo2.esRelajado().shouldBeTrue()
    }
    it("un vuelo de charter:"){
      vuelo3.esRelajado().shouldBeTrue()
    }
  }

  //Requerimiento 3
  describe("sePuedeVender...") {
    describe("Se pueden vender pasajes con criterio 'segura'") {
      LineaAerea.cambiarCriterio(Segura)
      it("para un vuelo de pasajeros:") {
        LineaAerea.sePuedeVender(pasajePasajero).shouldBeTrue()
      }
      it("para un vuelo de carga:") {
        LineaAerea.sePuedeVender(pasajeCarga).shouldBeTrue()
      }
      it("para un vuelo de charter:") {
        LineaAerea.sePuedeVender(pasajeCharter).shouldBeTrue()
      }
    }
    describe("No se pueden vender pasajes con criterio 'segura'") {
      it("para un vuelo de pasajeros:") {
        vuelo1.asientosOcupados = 98
        LineaAerea.sePuedeVender(pasajePasajero).shouldBeFalse()
      }
      it("para un vuelo de carga:") {
        vuelo2.asientosOcupados = 8
        LineaAerea.sePuedeVender(pasajeCarga).shouldBeFalse()
      }
      it("para un vuelo de charter:") {
        vuelo3.asientosOcupados = 73
        LineaAerea.sePuedeVender(pasajeCharter).shouldBeFalse()
      }
    }

    describe("Se pueden vender pasajes con criterio 'LaxaFija':") {
      LineaAerea.cambiarCriterio(LaxaFija)
      it("para un vuelo de pasajeros ") {
        vuelo1.asientosOcupados = 105
        LineaAerea.sePuedeVender(pasajePasajero).shouldBeTrue()
      }
      it("para un vuelo de carga:") {
        vuelo2.asientosOcupados = 15
        LineaAerea.sePuedeVender(pasajeCarga).shouldBeTrue()
      }
      it("para un vuelo de charter:") {
        vuelo3.asientosOcupados = 80
        LineaAerea.sePuedeVender(pasajeCharter).shouldBeTrue()
      }
    }
    describe("No se pueden vender pasajes con criterio 'LaxaFija':") {
      LineaAerea.cambiarCriterio(LaxaFija)
      it("para un vuelo de pasajeros ") {
        vuelo1.asientosOcupados = 110
        LineaAerea.sePuedeVender(pasajePasajero).shouldBeFalse()
      }
      it("para un vuelo de carga:") {
        vuelo2.asientosOcupados = 20
        LineaAerea.sePuedeVender(pasajeCarga).shouldBeFalse()
      }
      it("para un vuelo de charter:") {
        vuelo3.asientosOcupados = 85
        LineaAerea.sePuedeVender(pasajeCharter).shouldBeFalse()
      }
    }

    describe("Se pueden vender pasajes  con criterio 'LaxaPorcentual'") {
      LineaAerea.cambiarCriterio(LaxaPorcentual)
      it("para un vuelo de pasajeros:") {
        vuelo1.asientosOcupados = 105
        LineaAerea.sePuedeVender(pasajePasajero).shouldBeTrue()
      }
      it("para un vuelo de carga:") {
        vuelo2.asientosOcupados = 10
        LineaAerea.sePuedeVender(pasajeCarga).shouldBeTrue()
      }
      it("para un vuelo de charter:") {
        vuelo3.asientosOcupados = 80
        LineaAerea.sePuedeVender(pasajeCharter).shouldBeTrue()
      }
    }

    describe("No se pueden vender pasajes  con criterio 'LaxaPorcentual'") {
      LineaAerea.cambiarCriterio(LaxaPorcentual)
      it("para un vuelo de pasajeros:") {
        vuelo1.asientosOcupados = 110
        LineaAerea.sePuedeVender(pasajePasajero).shouldBeFalse()
      }
      it("para un vuelo de carga :") {
        vuelo2.asientosOcupados = 11
        LineaAerea.sePuedeVender(pasajeCarga).shouldBeFalse()
      }
      it("para un vuelo de charter:") {
        vuelo3.asientosOcupados = 82
        LineaAerea.sePuedeVender(pasajeCharter).shouldBeFalse()
      }
    }

    describe("No se pueden vender pasajes con criterio 'Pandemia'"){
      LineaAerea.cambiarCriterio(Pandemia)
      it("para un vuelo de pasajeros:") {
        LineaAerea.sePuedeVender(pasajePasajero).shouldBeFalse()
      }
      it("para un vuelo de carga:") {
        LineaAerea.sePuedeVender(pasajeCarga).shouldBeFalse()
      }
      it("para un vuelo de charter:") {
        LineaAerea.sePuedeVender(pasajeCharter).shouldBeFalse()
      }
    }
    //Requerimiento 4
    describe("El precio de venta de un pasaje segun...") {
      it("de un vuelo de pasajeros es:"){
        pasajePasajero.vuelo.preciosSegunPolitica().shouldBe(99999)
      }
    }
  }

})
