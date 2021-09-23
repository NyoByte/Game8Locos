package pe.edu.ulima.game_8_locos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GanadorDialog: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_ganador)

        val winner = intent.getBundleExtra("data")?.getString("ganador")
        findViewById<TextView>(R.id.tviGanador).text = winner

        findViewById<Button>(R.id.btnOk).setOnClickListener { v: View ->

            val intent = Intent(this, LobbyActivity::class.java)
            val bundle = Bundle()
            bundle.putString("ganador",winner)
            intent.putExtra("data",bundle)
            startActivityForResult(intent,20)
            finish()
        }
    }
}