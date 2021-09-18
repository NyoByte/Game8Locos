package pe.edu.ulima.game_8_locos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text
import pe.edu.ulima.game_8_locos.views.ApectRatioImageView
import pe.edu.ulima.game_8_locos.views.CardView
import kotlin.math.max
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    private var deck: MutableList<Card> = ArrayList()
    private var players:MutableList<Player> = ArrayList()

    private val numPlayers: Int = 3

    private var turn: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("es: ")
        //val card1 = findViewById<CardView>(R.id.card1)
        //card1.setCard(CardActivity("8","trebol"))

        //Crear las 52 cartas
        this.createDeck()

        //Repartir cartas
        this.dealCards()

        //Definir primer jugador
        this.turn = (0..2).random()
        findViewById<TextView>(R.id.tviCurrPlayer).text = this.players[this.turn].name

        //Colocar carta en la mesa
        this.placeCardOnTable()
        //Mostrar cartas del jugador actual
        this.showCards()
        //Mostrar num de cartas de los otros jugadores y del maso
        this.showRemainingCards()

        findViewById<Button>(R.id.btnPass).setOnClickListener {_: View ->
            if(this.turn < this.players.size - 1){
                this.turn += 1
            }else{
                this.turn = 0
            }
            this.showCards()
            this.showRemainingCards()
        }

        findViewById<Button>(R.id.btnSort).setOnClickListener {_: View ->
            this.players[this.turn].hand = this.sortCards(this.players[this.turn].hand).toMutableList()
            this.showCards()
        }
    }

    private fun placeCardOnTable() {
        val cardTable: CardView = findViewById(R.id.cardTable)

        cardTable.setCard(this.deck.removeLast())
    }

    private fun showRemainingCards() {
        //Mostrar cartas que quedan en el mazo
        val tviMazo: TextView = findViewById(R.id.tviMazo)
        tviMazo.text = "Mazo: ${this.deck.size} cartas"

        //Mostrar cartas restantes de jugadores
        var j = 1
        for (i in 0 until players.size){
            if(i != this.turn){
                val resId = resources.getIdentifier("tviPlayer$j", "id", packageName)
                val tviPlayer: TextView = findViewById(resId)
                tviPlayer.text = "${players[i].name}: ${players[i].hand.size} cartas"
                j += 1
            }
        }
    }

    private fun showCards() {
        val currPlayer = players[this.turn]
        for (i in 0..7){
            val resId = resources.getIdentifier("card"+(i+1), "id", packageName)
            val card: CardView = findViewById(resId)
            card.setCard(currPlayer.hand[i])
        }
    }

    private fun dealCards() {
        for (i in 1..numPlayers){
            val currHand: MutableList<Card> = ArrayList()
            for(j in 1..8){
                currHand.add(this.deck.removeLast())
            }
            this.players.add(Player(i,"Player $i", currHand))
            Log.i("MainActivity", "Dealed ${currHand.size} to player $i")
        }
    }


    private fun createDeck() {
        for (i in 1..13){
            this.deck.add(Card(i,"coco"))
            this.deck.add(Card(i,"corazon"))
            this.deck.add(Card(i,"espada"))
            this.deck.add(Card(i,"trebol"))
        }
        this.deck.shuffle()

        val tviMazo: TextView = findViewById(R.id.tviMazo)
        tviMazo.text = this.deck.size.toString()
    }

    private fun sortCards(cards: List<Card>): List<Card>{
        val corazones = cards.filter { card ->  card.suit == "corazon"}
        val espadas = cards.filter { card ->  card.suit == "espada"}
        val cocos = cards.filter { card ->  card.suit == "coco"}
        val treboles = cards.filter { card ->  card.suit == "trebol"}

        return sortSameSuit(corazones) + sortSameSuit(espadas) + sortSameSuit(cocos) + sortSameSuit(treboles)
    }

    private fun sortSameSuit(cards: List<Card>): List<Card>{
        if(cards.count()<2){
            return cards
        }
        val pivot = cards[cards.count()/2]

        val equal = cards.filter { card -> card.valor == pivot.valor }

        val minor = cards.filter { card -> card.valor < pivot.valor }

        val greater = cards.filter { card -> card.valor > pivot.valor }

        return sortSameSuit(minor) + equal + sortSameSuit(greater)
    }

}