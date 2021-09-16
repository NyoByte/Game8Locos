package pe.edu.ulima.game_8_locos.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import pe.edu.ulima.game_8_locos.CardActivity
import pe.edu.ulima.game_8_locos.R

class CardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val cover: ImageView
    private val valor: TextView
    private val suit:  TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_card, this,true)
        cover = view.findViewById(R.id.aviCover)
        valor = view.findViewById(R.id.tviValor)
        suit = view.findViewById(R.id.tviSuit)

        orientation = VERTICAL
    }

    fun setCard(card: CardActivity){
        this.valor.text = card.valor
        this.suit.text = card.suit
        //this.cover.image = card.cover
    }
}