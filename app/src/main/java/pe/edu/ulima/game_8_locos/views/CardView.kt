package pe.edu.ulima.game_8_locos.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import pe.edu.ulima.game_8_locos.Card
import pe.edu.ulima.game_8_locos.R

interface OnCardClickListener {
    fun onClick(valor:String, suit:String)
}

class CardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val suitUp: ApectRatioImageView
    private val valor: TextView
    private val suitDown: ApectRatioImageView
    private var listener:OnCardClickListener? = null
    private var suitC:String

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_card, this,true)
        suitUp = view.findViewById(R.id.aviSuitUp)
        valor = view.findViewById(R.id.tviValor)
        suitDown = view.findViewById(R.id.aviSuitDown)

        orientation = VERTICAL

        val a = context.obtainStyledAttributes(attrs, R.styleable.CardView)

        val suit = a.getString(R.styleable.CardView_suit)
        this.suitC = suit.toString()
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
        this.suitC = card.suit

        when (card.suit) {
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
            "trebol" -> {
                this.suitUp.setImageResource(R.drawable.trebol)
                this.suitDown.setImageResource(R.drawable.trebol)
                this.valor.setTextColor(Color.parseColor("#000000"))
            }
        }

        when(card.valor){
            1 -> this.valor.text = "A"
            11 -> this.valor.text = "J"
            12 -> this.valor.text = "Q"
            13 -> this.valor.text = "K"
            else -> this.valor.text = card.valor.toString()
        }
        if(card.valor == 10){
            this.valor.setTextSize(TypedValue.COMPLEX_UNIT_SP,26f)
        }else{
            this.valor.setTextSize(TypedValue.COMPLEX_UNIT_SP,40f)
        }
    }

    fun setOnCardClickListener(listener:OnCardClickListener) {
        this.listener = listener
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.i("CardView","TouchEvent")
        listener?.onClick(this.valor.text.toString(), this.suitC)
        invalidate()
        return super.onTouchEvent(event)
    }
}