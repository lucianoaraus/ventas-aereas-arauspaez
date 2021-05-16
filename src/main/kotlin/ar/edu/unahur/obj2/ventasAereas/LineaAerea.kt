package ar.edu.unahur.obj2.ventasAereas

class LineaAerea{
    var aviones = mutableListOf<Avion>()
    // criterios? "La empresa decide si se puede vender o no pasajes de vuelo"
}

abstract class Criterio{
    // Se deben poder cambiar
}

object Segura : Criterio()

object LaxaFija : Criterio()

object LaxaPorcentual : Criterio()

object Pandemia : Criterio()
