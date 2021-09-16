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

    private val suit: ImageView
    private val valor: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_card, this,true)
        suit = view.findViewById(R.id.aviSuitUp)
        valor = view.findViewById(R.id.tviValor)

        orientation = VERTICAL
    }

    fun setCard(card: CardActivity){
        this.valor.text = card.valor
        if(card.suit=="coco"){
            println("coco")
        }else if(card.suit=="corazon"){
            println("corazon")
        }else if(card.suit=="espada"){
            println("espada")
        }else{
            println("trebol")
        }
        //this.suit.image = card.suit
    }
}