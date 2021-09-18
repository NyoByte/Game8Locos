package pe.edu.ulima.game_8_locos.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import pe.edu.ulima.game_8_locos.Card
import pe.edu.ulima.game_8_locos.R

class CardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val suitUp: ApectRatioImageView
    private val valor: TextView
    private val suitDown: ApectRatioImageView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_card, this,true)
        suitUp = view.findViewById(R.id.aviSuitUp)
        valor = view.findViewById(R.id.tviValor)
        suitDown = view.findViewById(R.id.aviSuitDown)

        orientation = VERTICAL

        val a = context.obtainStyledAttributes(attrs, R.styleable.CardView)

        val suit = a.getString(R.styleable.CardView_suit)
        when(suit){
            "coco" -> {
                this.suitUp.setImageResource(R.drawable.coco)
                this.suitDown.setImageResource(R.drawable.coco)
                this.valor.setTextColor(Color.parseColor("#FF0000"))
            }
            "corazon" -> {
                this.suitUp.setImageResource(R.drawable.corazon)
                this.suitDown.setImageResource(R.drawable.corazon)
                this.valor.setTextColor(Color.parseColor("#FF0000"))
            }
            "espada" -> {
                this.suitUp.setImageResource(R.drawable.espada)
                this.suitDown.setImageResource(R.drawable.espada)
                this.valor.setTextColor(Color.parseColor("#000000"))
            }
            "trebol"-> {
                this.suitUp.setImageResource(R.drawable.trebol)
                this.suitDown.setImageResource(R.drawable.trebol)
                this.valor.setTextColor(Color.parseColor("#000000"))
            }
        }
        this.valor.text =a.getString( R.styleable.CardView_valor)
    }

    fun setCard(card: Card){
        if(card.suit=="coco"){
            this.suitUp.setImageResource(R.drawable.coco)
            this.suitDown.setImageResource(R.drawable.coco)
            this.valor.setTextColor(Color.parseColor("#FF0000"))
        }else if(card.suit=="corazon"){
            this.suitUp.setImageResource(R.drawable.corazon)
            this.suitDown.setImageResource(R.drawable.corazon)
            this.valor.setTextColor(Color.parseColor("#FF0000"))
        }else if(card.suit=="espada"){
            this.suitUp.setImageResource(R.drawable.espada)
            this.suitDown.setImageResource(R.drawable.espada)
            this.valor.setTextColor(Color.parseColor("#000000"))
        }else{
            this.suitUp.setImageResource(R.drawable.trebol)
            this.suitDown.setImageResource(R.drawable.trebol)
            this.valor.setTextColor(Color.parseColor("#000000"))
        }

        when(card.valor){
            1 -> this.valor.text = "A";
            11 -> this.valor.text = "J"
            12 -> this.valor.text = "Q"
            13 -> this.valor.text = "K"
            else -> this.valor.text = card.valor.toString()

        }
        //this.suit.image = card.suit
    }
}