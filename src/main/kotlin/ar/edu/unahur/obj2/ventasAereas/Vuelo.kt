package ar.edu.unahur.obj2.ventasAereas


import java.time.LocalDate


abstract class Vuelo(val fecha : LocalDate,val avion : Avion,val origen : String,val destino : String,val precio : Double){

    abstract var asientosDisponibles : Int
    abstract var asientosOcupados : Int // Asientos vendidos
    lateinit var politicaPrecios: PoliticaPrecios
    fun preciosSegunPolitica() = politicaPrecios.precioAsiento(this)
    fun cambiarPolitica(nuevaPolitica : PoliticaPrecios) { politicaPrecios = nuevaPolitica }

    //Requerimiento 1
    fun asientosLibres() = asientosDisponibles - asientosOcupados
    //Requerimiento 2
    fun esRelajado() = avion.alturaCabina > 4 && asientosDisponibles < 100
    //Requerimiento 6
    var importeTotal = 0.0
    //Requerimiento 7
    fun pesoPasajeros() = asientosOcupados.toDouble() * IATA.pesoEstandar
    abstract fun pesoDeLaCarga() : Double
    fun pesoMaximo() = (avion.pesoAvion + this.pesoPasajeros() + this.pesoDeLaCarga())

    //Requerimiento 8
    var pasajes = mutableListOf<Pasaje>()
    fun tienePasajeParaDestino(dniPersona: Int,destino: String) = this.tienePasaje(dniPersona) && this.destino == destino

    //Requerimiento 10
    fun tienePasaje(dni: Int) = pasajes.any{ it.dniPasajero == dni}
    fun tienenPasaje(dni1: Int,dni2: Int) = this.tienePasaje(dni1) && this.tienePasaje(dni2)
}

class VueloPasajeros(fecha: LocalDate,avion: Avion,origen: String,destino: String,precio: Double) :
    Vuelo(fecha,avion,origen,destino,precio) {
    override var asientosDisponibles = avion.totalAsientos
    override var asientosOcupados = 0
    override fun pesoDeLaCarga() = asientosOcupados.toDouble() * LineaAerea.equipaje
}

class VueloCarga(fecha: LocalDate,avion: Avion,origen: String,destino: String,precio: Double, val carga: Double) :
    Vuelo(fecha,avion,origen,destino,precio) {
    override var asientosDisponibles = 10
    override var asientosOcupados = 0
    override fun pesoDeLaCarga() = carga + 700.0
}

class VueloCharter(fecha: LocalDate,avion: Avion,origen: String,destino: String,precio: Double,pasajerosVIP: Int) :
    Vuelo(fecha,avion,origen,destino,precio){
    override var asientosDisponibles = avion.totalAsientos - 25
    override var asientosOcupados = pasajerosVIP
    //Requerimiento 7
    override fun pesoDeLaCarga() = 5000.0
}

