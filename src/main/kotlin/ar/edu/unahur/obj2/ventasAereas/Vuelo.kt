package ar.edu.unahur.obj2.ventasAereas

import java.sql.Time
import java.time.LocalDate
import java.util.*

abstract class Vuelo(val fecha : LocalDate,val avion : Avion,val origen : String,val destino : String,val precio : Double){
    // Precio: Existe un precio estándar

    abstract var asientosDisponibles : Int
    abstract var asientosOcupados : Int // Asientos vendidos
    fun hayAsientosDisponibles() = asientosDisponibles > 0
    lateinit var politicaPrecios: PoliticaPrecios
    fun preciosSegunPolitica() = politicaPrecios.precioAsiento(this)
    fun cambiarPolitica(nuevaPolitica : PoliticaPrecios) { politicaPrecios = nuevaPolitica }

    //Requerimiento 1
    fun asientosLibres() = asientosDisponibles - asientosOcupados
    //Requerimiento 2
    fun esRelajado() = avion.alturaCabina > 4 && asientosDisponibles < 100
    //Requerimiento 3
}

class VueloPasajeros(fecha: LocalDate,avion: Avion,origen: String,destino: String,precio: Double) :
    Vuelo(fecha,avion,origen,destino,precio) {
    override var asientosDisponibles = avion.totalAsientos
    override var asientosOcupados = 0
    // Todos sus asientos disponibles
}

class VueloCarga(fecha: LocalDate,avion: Avion,origen: String,destino: String,precio: Double) :
    Vuelo(fecha,avion,origen,destino,precio) {
    override var asientosDisponibles = 10
    override var asientosOcupados = 0
    // 10 Asientos disponibles
}

class VueloCharter(fecha: LocalDate,avion: Avion,origen: String,destino: String,precio: Double,pasajerosVIP: Int) :
    Vuelo(fecha,avion,origen,destino,precio){
    override var asientosDisponibles = avion.totalAsientos - 25
    override var asientosOcupados = pasajerosVIP
    // (Cantidad de asientos - 25) + Pasajeros VIP = total asientos
}

