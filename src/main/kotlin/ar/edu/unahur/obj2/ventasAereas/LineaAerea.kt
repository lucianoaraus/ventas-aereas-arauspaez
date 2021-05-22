package ar.edu.unahur.obj2.ventasAereas

object LineaAerea{
    lateinit var criterio : Criterio
    var aviones = mutableListOf<Avion>()
    fun agregarAviones(avion: Avion) { aviones.add(avion) }
    fun cambiarCriterio(nuevoCriterio: Criterio) { criterio = nuevoCriterio }

    //Requerimiento 3
    fun sePuedeVender(pasaje: Pasaje) = criterio.ventaPermitida(pasaje.vuelo)
    fun venderPasaje(pasaje: Pasaje){
        if(this.sePuedeVender(pasaje)) {
            pasaje.vuelo.asientosOcupados += 1
            pasaje.vuelo.asientosDisponibles -= 1
            pasaje.vuelo.importeTotal += pasaje.importe //Requerimiento 6
        } else{
            throw Exception("No se pueden vender pasajes")
        }
    }
    //Requerimiento 7
    var equipaje = 200.0 //Peso de equipaje por defecto
}


abstract class Criterio{
    abstract fun ventaPermitida(vuelo: Vuelo): Boolean
}
object Segura : Criterio(){
    override fun ventaPermitida(vuelo: Vuelo) = vuelo.asientosLibres() >= 3
}
object LaxaFija : Criterio() {
    override fun ventaPermitida(vuelo: Vuelo) = vuelo.asientosOcupados < vuelo.asientosDisponibles + 10
}
object LaxaPorcentual : Criterio() {
    override fun ventaPermitida(vuelo: Vuelo) = vuelo.asientosOcupados < (vuelo.asientosDisponibles * 1.1).toInt()
}
object Pandemia : Criterio() {
    override fun ventaPermitida(vuelo: Vuelo) : Boolean = false
}