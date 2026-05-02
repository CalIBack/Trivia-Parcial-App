package com.example.parcial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val llPantallaInicio = findViewById<LinearLayout>(R.id.llPantallaInicio)
        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val tvCategoria = findViewById<TextView>(R.id.tvCategoria)
        val spnCategoria = findViewById<Spinner>(R.id.spnCategoria)
        val btnInicio = findViewById<Button>(R.id.btnInicio)
        val btnTema = findViewById<Button>(R.id.btnTema)

        fun aplicarTema() {
            llPantallaInicio.setBackgroundColor(Temas.color("fondo"))
            etUsuario.setTextColor(Temas.color("texto"))
            tvCategoria.setTextColor(Temas.color("texto"))
        }

        aplicarTema()

        btnTema.setOnClickListener {
            Temas.modoOscuro = !Temas.modoOscuro
            recreate()
        }

        val categorias = arrayOf(
            getString(R.string.cat_seleccionar),
            getString(R.string.cat_juegos),
            getString(R.string.cat_historia),
            getString(R.string.cat_cine),
            getString(R.string.cat_musica)
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnCategoria.adapter = adapter

        btnInicio.setOnClickListener {

            val nombre = etUsuario.text.toString().trim()
            val elegida = spnCategoria.selectedItemPosition
            val categoria = spnCategoria.selectedItem.toString()

            if (nombre.isEmpty()) {
                etUsuario.error = getString(R.string.error_nombre)
                etUsuario.requestFocus()
            }
            else if (elegida == 0) {
                Toast.makeText(this, getString(R.string.error_categoria), Toast.LENGTH_SHORT).show()
            }
            else {
                val datosJugador = Jugador(nombre, categoria)
                val intent = Intent(this, TriviaActivity::class.java)

                intent.putExtra("DATOS_JUGADOR", datosJugador)

                startActivity(intent)
            }
        }
    }
}