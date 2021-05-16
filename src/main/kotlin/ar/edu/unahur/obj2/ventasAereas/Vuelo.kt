package ar.edu.unahur.obj2.ventasAereas

import java.sql.Time
import java.util.*

abstract class Vuelo(val fecha : Date,val tiempoVuelo : Time,val avion : Avion,val origen : String,val destino : String,val precio : Int){
    // Precio: Existe un precio estándar
}

class VueloPasajeros(fecha: Date,tiempoVuelo: Time,avion: Avion,origen: String,destino: String,precio: Int) :
    Vuelo(fecha,tiempoVuelo,avion,origen,destino,precio) {
    // Todos sus asientos disponibles
}

class VueloCarga(fecha: Date,tiempoVuelo: Time,avion: Avion,origen: String,destino: String,precio: Int) :
    Vuelo(fecha,tiempoVuelo,avion,origen,destino,precio) {
    // 10 Asientos disponibles
}

class VueloCharter(fecha: Date,tiempoVuelo: Time,avion: Avion,origen: String,destino: String,precio: Int) :
    Vuelo(fecha,tiempoVuelo,avion,origen,destino,precio) {
    // (Cantidad de asientos - 25) + Pasajeros VIP = total asientos
}


abstract class PoliticaPrecios{
    // El precio depende de la cantidad de asientos vendidos en el vuelo
}

object Estricta : PoliticaPrecios() {
    // Todos los asientos se venden al precio estándar
}

object VentaAnticipada : PoliticaPrecios() {
    // 70% OFF -> Si tiene menos de 40 pasajes vendidos
    // 40% OFF -> Si tiene entre 40 y 79 pasajes vendidos
    // Sino    -> Precio estándar
}

object Remate : PoliticaPrecios() {
    // 75% OFF -> Si tiene mas de 30 asientos libres
    // Sino    -> 50% OFF
}