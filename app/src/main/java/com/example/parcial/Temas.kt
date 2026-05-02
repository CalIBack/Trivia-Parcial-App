package com.example.parcial

import androidx.core.graphics.toColorInt
object Temas {
    var modoOscuro: Boolean = false

    private val oscuro = mapOf(
        "fondo" to "#121212",
        "texto" to "#FFFFFF",
        "juegos" to "#002B36",
        "historia" to "#2D1B33",
        "cine" to "#0D1B2A",
        "musica" to "#310E0E"
    )

    private val claro = mapOf(
        "fondo" to "#F5F5F5",
        "texto" to "#000000",
        "juegos" to "#B2EBF2",
        "historia" to "#E1BEE7",
        "cine" to "#BBDEFB",
        "musica" to "#FFCDD2"
    )

    fun color(clave: String): Int =
        (if (modoOscuro) oscuro[clave]!! else claro[clave]!!).toColorInt()
}