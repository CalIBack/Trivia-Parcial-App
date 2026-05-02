package com.example.parcial

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class TriviaActivity : AppCompatActivity() {

    private var respuestaCorrecta: String = ""

    private val diccionarioTrivia = mapOf(
        "juegos" to RecursosTrivia(
            R.string.trivia_juegos_enunciado,
            listOf(R.string.trivia_juegos_op1, R.string.trivia_juegos_op2, R.string.trivia_juegos_op3, R.string.trivia_juegos_op4),
            R.string.trivia_juegos_op2
        ),
        "historia" to RecursosTrivia(
            R.string.trivia_historia_enunciado,
            listOf(R.string.trivia_historia_op1, R.string.trivia_historia_op2, R.string.trivia_historia_op3, R.string.trivia_historia_op4),
            R.string.trivia_historia_op4
        ),
        "cine" to RecursosTrivia(
            R.string.trivia_cine_enunciado,
            listOf(R.string.trivia_cine_op1, R.string.trivia_cine_op2, R.string.trivia_cine_op3, R.string.trivia_cine_op4),
            R.string.trivia_cine_op3
        ),
        "musica" to RecursosTrivia(
            R.string.trivia_musica_enunciado,
            listOf(R.string.trivia_musica_op1, R.string.trivia_musica_op2, R.string.trivia_musica_op3, R.string.trivia_musica_op4),
            R.string.trivia_musica_op1
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trivia)

        val llPantallaTrivia = findViewById<LinearLayout>(R.id.llPantallaTrivia)
        val tvBienvenida = findViewById<TextView>(R.id.tvBienvenida)
        val tvPregunta = findViewById<TextView>(R.id.tvPregunta)
        val rgOpciones = findViewById<RadioGroup>(R.id.rgOpciones)
        val btnResponder = findViewById<Button>(R.id.btnResponder)
        val btnTema = findViewById<Button>(R.id.btnTema)

        val listaBotones = listOf(
            findViewById<RadioButton>(R.id.rbOpcion1),
            findViewById<RadioButton>(R.id.rbOpcion2),
            findViewById<RadioButton>(R.id.rbOpcion3),
            findViewById<RadioButton>(R.id.rbOpcion4)
        )

        val perfil = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("DATOS_JUGADOR", Jugador::class.java)
        } else {
            intent.getSerializableExtra("DATOS_JUGADOR") as? Jugador
        }

        fun aplicarTema(categoria: String = "") {
            val fondoCat = if (categoria.isNotEmpty()) categoria else "fondo"
            llPantallaTrivia.setBackgroundColor(Temas.color(fondoCat))
            tvBienvenida.setTextColor(Temas.color("texto"))
            tvPregunta.setTextColor(Temas.color("texto"))
            listaBotones.forEach { it.setTextColor(Temas.color("texto")) }
        }


        btnTema.setOnClickListener {
            Temas.modoOscuro = !Temas.modoOscuro
            recreate()
        }

        if (perfil != null) {
            tvBienvenida.text = getString(R.string.saludo_bienvenida, perfil.usuario, perfil.categoria)

            val key = perfil.categoria.lowercase()
            val pregunta = diccionarioTrivia[key]

            pregunta?.let { p ->
                tvPregunta.text = getString(p.enunciadoId)
                p.opcionesIds.forEachIndexed { i, opId -> listaBotones[i].text = getString(opId) }
                respuestaCorrecta = getString(p.correctaId)
                aplicarTema(key)
            }
        } else {
            // Solo si no hay perfil se usa fondo genérico
            aplicarTema()
        }

        btnResponder.setOnClickListener {
            evaluarRespuesta(rgOpciones)
        }
    }

    private fun evaluarRespuesta(rg: RadioGroup) {
        val id = rg.checkedRadioButtonId
        if (id != -1) {
            val seleccionado = findViewById<RadioButton>(id)
            val mensajeId = if (seleccionado.text == respuestaCorrecta) R.string.msg_correcto else R.string.msg_incorrecto

            val textoToast = if (mensajeId == R.string.msg_incorrecto)
                getString(mensajeId, respuestaCorrecta) else getString(mensajeId)

            Toast.makeText(this, textoToast, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, getString(R.string.seleccionar_opcion), Toast.LENGTH_SHORT).show()
        }
    }
}