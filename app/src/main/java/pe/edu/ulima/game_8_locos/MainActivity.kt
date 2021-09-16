package pe.edu.ulima.game_8_locos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pe.edu.ulima.game_8_locos.views.ApectRatioImageView
import pe.edu.ulima.game_8_locos.views.CardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val card1 = findViewById<CardView>(R.id.card1)
        card1.setCard(CardActivity("A","corazon"))
    }
}