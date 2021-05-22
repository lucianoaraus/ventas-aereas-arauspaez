package ar.edu.unahur.obj2.ventasAereas

interface PoliticaPrecios{
    fun precioAsiento(vuelo: Vuelo) : Double
}

object Estricta : PoliticaPrecios {
    override fun precioAsiento(vuelo: Vuelo) = vuelo.precio
}

object VentaAnticipada : PoliticaPrecios {
    override fun precioAsiento(vuelo: Vuelo): Double {
        return when {
            vuelo.asientosOcupados < 40 -> vuelo.precio * 0.3
            vuelo.asientosOcupados in (40..79)  -> vuelo.precio * 0.6
            else -> vuelo.precio
        }
    }
}

object Remate : PoliticaPrecios {
    override fun precioAsiento(vuelo: Vuelo): Double {
        return if (vuelo.asientosLibres() > 30){
            vuelo.precio * 0.25
        }
        else vuelo.precio * 0.5
    }
}