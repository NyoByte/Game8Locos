package pe.edu.ulima.game_8_locos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NextplayerDialog: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_nextplayer)

        val nextPlayer = intent.getBundleExtra("data")?.getString("player")
        findViewById<TextView>(R.id.tviNextPlayer).text = nextPlayer

        findViewById<Button>(R.id.btnOk).setOnClickListener { v: View ->
            finish()
        }
    }
}