package pe.edu.ulima.game_8_locos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LobbyActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)

        findViewById<Button>(R.id.btnNewGame).setOnClickListener { _: View ->
            // Pasar al activity main
            val intent: Intent = Intent()
            intent.setClass(this, MainActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnPlay).setOnClickListener { _: View ->
            // Pasar al activity main
            val intent: Intent = Intent()
            intent.setClass(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}