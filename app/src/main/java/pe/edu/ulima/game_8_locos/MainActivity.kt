package pe.edu.ulima.game_8_locos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pe.edu.ulima.game_8_locos.views.CardTableView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fviCardTable = findViewById<CardTableView>(R.id.fviCardTable)
        fviCardTable.ratio = 1.5f
    }
}