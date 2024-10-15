package com.example.thegameofpig

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.thegameofpig.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    // Declaramos variables para el binding y las puntuaciones
    lateinit var binding: ActivityMainBinding
    val random: Random = Random.Default

    var player1PuntCont: Int = 0
    var player2PuntCont: Int = 0
    var jugadorEnJuego: Int = 1 // 1 para Player1, 2 para Player2

    // Variable para controlar si es la primera vez que se lanza el dado
    var primerLanzamiento: Boolean = true

    // Variable de puntos con el cual se elegirá el ganador
    val puntosGanar = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // setContentView(R.layout.activity_main)

        // Inicializamos el binding y,
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Cambiamos la vista del contenido a través del binding
        setContentView(binding.root)

        // El texto del centro se utilizará para mostrar el Ganador, por ahora estara oculto
        binding.textViewCentro.visibility = View.INVISIBLE

        // Asignamos el evento al botón "LANZAR" para lanzar el dado
        binding.buttonLanzar.setOnClickListener()
        {
            lanzarDado()
        }

        // Asignamos el evento al botón "TURNO" para cambiar de jugador
        binding.buttonTurno.setOnClickListener()
        {
            cambiarTurno()
        }

        // Asignamos el evento al botón "NEW!" para reiniciar el juego
        binding.buttonNew.setOnClickListener()
        {
            reiniciarJuego()
        }

        // Inicializamos los valores en la interfaz de usuario
        actualizarInterfaz()
    }

    // Método para simular el lanzamiento de un dado
    fun lanzarDado() {
        val dado: Int = random.nextInt(6) + 1 // Generamos un número del 1 al 6

        // Condicion para mostrar el mensaje de Lanzado solo la primera vez de juego
        if (primerLanzamiento) {
            Toast.makeText(this, "LANZADO!!", Toast.LENGTH_SHORT).show()
            primerLanzamiento = false; // Cambiamos a False una vez mostrado
        }

        if (dado == 1) {
            // Si el dado es 1, reseteamos la puntuación del jugador actual y cambiamos el turno
            if (jugadorEnJuego == 1) {
                player1PuntCont = 0
                binding.textPuntPlayer1.text = player1PuntCont.toString()
            } else {
                player2PuntCont = 0
                binding.textPuntPlayer2.text = player2PuntCont.toString()
            }

            cambiarTurno() // Y cambiamos el turno del jugador
        } else {
            // Sumamos el valor del dado a la puntuación del jugador actual y actualiza la interfaz
            actualizarPuntuacion(dado)

            // Comprobamos si el jugador actual ha ganado
            if (jugadorEnJuego == 1 && player1PuntCont >= puntosGanar)
            {
                mostrarGanador(1)
            } else if (jugadorEnJuego == 2 && player2PuntCont >= puntosGanar) {
                mostrarGanador(2)
            }
        }

        // Actualizamos la interfaz con los nuevos valores
        actualizarInterfaz()
    }

    // Método para cambiar el turno entre los jugadores
    fun cambiarTurno() {
        if (jugadorEnJuego == 1) {
            jugadorEnJuego = 2 // Si el jugador actual es 1, cambia al jugador 2
            Toast.makeText(this, "JUGADOR 2 EN JUEGO!!", Toast.LENGTH_SHORT).show()
        } else {
            jugadorEnJuego = 1 // Si no, cambia al jugador 1
            Toast.makeText(this, "JUGADOR 1 EN JUEGO!!", Toast.LENGTH_SHORT).show()

        }
        actualizarInterfaz() // Actualiza la interfaz para mostrar el turno actual
    }


    // Método para sumar el valor del dado a la puntuación del jugador actual
    fun actualizarPuntuacion(dado: Int) {
        if (jugadorEnJuego == 1) {
            player1PuntCont += dado
            binding.textPuntPlayer1.text = player1PuntCont.toString(); // Actualizamos el TextView
        } else {
            player2PuntCont += dado
            binding.textPuntPlayer2.text = player2PuntCont.toString(); // Actualizamos el textView
        }
    }

    // Método que actualiza los elementos de la interfaz de usuario (TextViews y botones) con los valores actuales
    fun actualizarInterfaz() {
        binding.textPuntPlayer1.text = player1PuntCont.toString()
        binding.textPuntPlayer2.text = player2PuntCont.toString()
        binding.buttonTurno.text = "JUGADOR ACTUAL: $jugadorEnJuego"
    }

    // Método para mostrar el ganador de la partida
    fun mostrarGanador(jugador: Int) {
        binding.textViewCentro.text = "¡EL JUGADOR $jugadorEnJuego HA GANADO!"
        binding.textViewCentro.visibility = View.VISIBLE // Mostramos el elemento
        // Ocultamos los TextView de los puntos
        binding.textPuntPlayer1.visibility = View.INVISIBLE
        binding.textPuntPlayer2.visibility = View.INVISIBLE
    }

    // Método para reiniciar el juego
    fun reiniciarJuego() {
        // Restablecemos los valores
        player1PuntCont = 0
        player2PuntCont = 0
        binding.textPuntPlayer1.text = "0"
        binding.textPuntPlayer2.text = "0"
        binding.textViewCentro.visibility = View.INVISIBLE
        binding.buttonTurno.text = "TURNO"
    }

}
