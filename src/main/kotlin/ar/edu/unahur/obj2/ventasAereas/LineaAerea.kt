package ar.edu.unahur.obj2.ventasAereas

object LineaAerea{
    lateinit var criterio : Criterio
    var aviones = mutableListOf<Avion>()
    // criterios? "La empresa decide si se puede vender o no pasajes de vuelo"
    fun cambiarCriterio(nuevoCriterio: Criterio) { criterio = nuevoCriterio }
}

abstract class Criterio{
    // Se deben poder cambiar
    abstract fun ventaPermitida(vuelvo: Vuelo): Boolean
}

object Segura : Criterio(){
    override fun ventaPermitida(vuelo: Vuelo) = vuelo.asientosLibres() >= 3
    // :shield: Segura: se pueden vender pasajes sobre los vuelos que tengan, al menos, 3 asientos libres.
}
object LaxaFija : Criterio() {
    override fun ventaPermitida(vuelo: Vuelo) = vuelo.asientosOcupados < vuelo.asientosDisponibles + 10
    // :money_mouth_face: Laxa fija: se permite vender en cada vuelo hasta 10 pasajes más de los asientos disponibles.
}
object LaxaPorcentual : Criterio() {
    override fun ventaPermitida(vuelo: Vuelo) = vuelo.asientosOcupados < vuelo.asientosDisponibles * 1.1
    // :100: Laxa porcentual: se permite vender en cada vuelo hasta un 10% más de los asientos disponibles.
}
object Pandemia : Criterio() {
    override fun ventaPermitida(vuelo: Vuelo) : Boolean = false
    // :mask: Pandemia: no se puede vender ningún pasaje.
}