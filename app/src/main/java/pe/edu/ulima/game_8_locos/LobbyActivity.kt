package pe.edu.ulima.game_8_locos

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LobbyActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)

        findViewById<Button>(R.id.btnNewGame).setOnClickListener { _: View ->
            // Pasar al activity main
            val intent = Intent(this, PregameDialog::class.java)
            startActivityForResult(intent,10)
        }

        findViewById<Button>(R.id.btnPlay).setOnClickListener { _: View ->
            // Pasar al activity main
            val intent = Intent(this, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putString("namePlayer1", findViewById<TextView>(R.id.tviJugador1).text.toString())
            bundle.putString("namePlayer2", findViewById<TextView>(R.id.tviJugador2).text.toString())
            bundle.putString("namePlayer3", findViewById<TextView>(R.id.tviJugador3).text.toString())
            intent.putExtra("data",bundle)
            println("---> hay data")
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 10){
            // NewGame
            if(resultCode == RESULT_OK){
                // Registro exitoso
                var name1 = data?.getBundleExtra("dialog_data")?.getString("namePlayer1")
                var name2 = data?.getBundleExtra("dialog_data")?.getString("namePlayer2")
                var name3 = data?.getBundleExtra("dialog_data")?.getString("namePlayer3")
                if(name1==""){
                    name1="Player 1"
                }
                if(name2==""){
                    name2="Player 2"
                }
                if(name3==""){
                    name3="Player 3"
                }
                findViewById<TextView>(R.id.tviJugador1).text = name1
                findViewById<TextView>(R.id.tviJugador2).text = name2
                findViewById<TextView>(R.id.tviJugador3).text = name3

                findViewById<Button>(R.id.btnPlay).setEnabled(true)
                findViewById<Button>(R.id.btnPlay).callOnClick()
            }
        }
    }

}