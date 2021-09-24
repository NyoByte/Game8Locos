package pe.edu.ulima.game_8_locos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class PregameDialog: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var thirdPlayer: Boolean = false
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_pregame)

        findViewById<Button>(R.id.btnAddPlayer).setOnClickListener { v: View ->
            findViewById<EditText>(R.id.namePlayer3).visibility = View.VISIBLE
            if(thirdPlayer){
                findViewById<EditText>(R.id.namePlayer3).visibility = View.INVISIBLE
                findViewById<Button>(R.id.btnAddPlayer).text = "+"
                thirdPlayer = false
            }else{
                findViewById<EditText>(R.id.namePlayer3).visibility = View.VISIBLE
                findViewById<Button>(R.id.btnAddPlayer).text = "-"
                thirdPlayer = true
            }
        }

        findViewById<Button>(R.id.btnReady).setOnClickListener { v: View ->
            val intent = Intent()
            val bundle = Bundle()

            bundle.putString("namePlayer1", findViewById<EditText>(R.id.namePlayer1).text.toString())
            bundle.putString("namePlayer2", findViewById<EditText>(R.id.namePlayer2).text.toString())
            bundle.putString("namePlayer3", findViewById<EditText>(R.id.namePlayer3).text.toString())
            bundle.putBoolean("thirdPlayer", thirdPlayer)
            intent.putExtra("dialog_data",bundle)

            setResult(RESULT_OK, intent)
            finish()
        }
    }
}