package ar.edu.unahur.obj2.ventasAereas

import java.sql.Time
import java.util.*

abstract class Vuelo(val fecha : Date,val tiempoVuelo : Time,val avion : Avion,val origen : String,val destino : String,val precio : Double){
    // Precio: Existe un precio estándar

    abstract var asientosDisponibles : Int
    abstract var asientosOcupados : Int // Asientos vendidos
    lateinit var politicaPrecios: PoliticaPrecios
    fun preciosSegunPolitica() = politicaPrecios.precioAsiento(this)
    fun cambiarPolitica(nuevaPolitica : PoliticaPrecios) { politicaPrecios = nuevaPolitica }

    //Requerimiento 1
    fun asientosLibres() = asientosDisponibles - asientosOcupados
}

class VueloPasajeros(fecha: Date,tiempoVuelo: Time,avion: Avion,origen: String,destino: String,precio: Double) :
    Vuelo(fecha,tiempoVuelo,avion,origen,destino,precio) {
    override var asientosDisponibles = avion.totalAsientos
    override var asientosOcupados = 0
    // Todos sus asientos disponibles
}

class VueloCarga(fecha: Date,tiempoVuelo: Time,avion: Avion,origen: String,destino: String,precio: Double) :
    Vuelo(fecha,tiempoVuelo,avion,origen,destino,precio) {
    override var asientosDisponibles = 10
    override var asientosOcupados = 0
    // 10 Asientos disponibles
}

class VueloCharter(fecha: Date,tiempoVuelo: Time,avion: Avion,origen: String,destino: String,precio: Double,pasajerosVIP: Int) :
    Vuelo(fecha,tiempoVuelo,avion,origen,destino,precio){
    override var asientosDisponibles = avion.totalAsientos - 25 - pasajerosVIP
    override var asientosOcupados = pasajerosVIP
    // (Cantidad de asientos - 25) + Pasajeros VIP = total asientos
}

