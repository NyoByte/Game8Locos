package pe.edu.ulima.game_8_locos

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LobbyActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)

        findViewById<Button>(R.id.btnNewGame).setOnClickListener { _: View ->
            // Pasar al activity main
            val intent = Intent(this, PregameDialog::class.java)
            startActivityForResult(intent, 10)
        }

        findViewById<Button>(R.id.btnPlay).setOnClickListener { _: View ->
            // Pasar al activity main
            val intent = Intent(this, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putString(
                "namePlayer1",
                findViewById<TextView>(R.id.tviJugador1).text.toString()
            )
            bundle.putString(
                "namePlayer2",
                findViewById<TextView>(R.id.tviJugador2).text.toString()
            )
            bundle.putString(
                "namePlayer3",
                findViewById<TextView>(R.id.tviJugador3).text.toString()
            )
            intent.putExtra("data", bundle)
            startActivityForResult(intent, 20)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10) {
            // NewGame
            if (resultCode == RESULT_OK) {
                // Registro exitoso
                reiniciar()
                var name1 = data?.getBundleExtra("dialog_data")?.getString("namePlayer1")
                var name2 = data?.getBundleExtra("dialog_data")?.getString("namePlayer2")
                var name3 = data?.getBundleExtra("dialog_data")?.getString("namePlayer3")
                if (name1 == "") {
                    name1 = "Player 1"
                }
                if (name2 == "") {
                    name2 = "Player 2"
                }
                if (name3 == "") {
                    name3 = "Player 3"
                }
                findViewById<TextView>(R.id.tviJugador1).text = name1
                findViewById<TextView>(R.id.tviJugador2).text = name2
                findViewById<TextView>(R.id.tviJugador3).text = name3

                findViewById<Button>(R.id.btnPlay).setEnabled(true)
            }
        } else if (requestCode == 20) {
            // EndGame
            if (resultCode == RESULT_OK) {
                val playerGanador = data?.getBundleExtra("data")?.getString("ganador")
                val listName: MutableList<String> = ArrayList()
                var idGanador: Int = 0
                listName.add(findViewById<TextView>(R.id.tviJugador1).text.toString())
                listName.add(findViewById<TextView>(R.id.tviJugador2).text.toString())
                listName.add(findViewById<TextView>(R.id.tviJugador3).text.toString())
                for (i in 0 until 2) {
                    if (playerGanador == listName[i]) {
                        idGanador = i
                        break
                    }
                }
                var j = 1

                for (i in 0 until 2) {
                    // Aumentar partidas
                    val partidaId = resources.getIdentifier("tviPartida$j", "id", packageName)
                    var tempPartida = Integer.parseInt(findViewById<TextView>(partidaId).text.toString())
                    tempPartida += 1
                    findViewById<TextView>(partidaId).text = tempPartida.toString()
                    // Aumentar Victorias
                    val victoriaId = resources.getIdentifier("tviVictoria$j", "id", packageName)
                    var tempVictoria = Integer.parseInt(findViewById<TextView>(victoriaId).text.toString())
                    // Aumentar Derrotas
                    val derrotaId = resources.getIdentifier("tviDerrota$j", "id", packageName)
                    var tempDerrota = Integer.parseInt(findViewById<TextView>(derrotaId).text.toString())
                    if(i==idGanador){
                        tempVictoria +=1
                    }else{
                        tempDerrota +=1
                    }
                    j += 1
                }
            }
        }
    }

    private fun reiniciar(){
        findViewById<TextView>(R.id.tviJugador1).text = "Player 1"
        findViewById<TextView>(R.id.tviJugador2).text = "Player 2"
        findViewById<TextView>(R.id.tviJugador3).text = "Player 3"

        findViewById<TextView>(R.id.tviPartida1).text = "0"
        findViewById<TextView>(R.id.tviPartida2).text = "0"
        findViewById<TextView>(R.id.tviPartida3).text = "0"

        findViewById<TextView>(R.id.tviVictoria1).text = "0"
        findViewById<TextView>(R.id.tviVictoria2).text = "0"
        findViewById<TextView>(R.id.tviVictoria3).text = "0"

        findViewById<TextView>(R.id.tviDerrota1).text = "0"
        findViewById<TextView>(R.id.tviDerrota2).text = "0"
        findViewById<TextView>(R.id.tviDerrota3).text = "0"

        findViewById<Button>(R.id.btnPlay).setEnabled(false)
    }
}