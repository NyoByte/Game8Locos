package pe.edu.ulima.game_8_locos

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import org.w3c.dom.Text
import pe.edu.ulima.game_8_locos.views.ApectRatioImageView
import pe.edu.ulima.game_8_locos.views.CardView
import pe.edu.ulima.game_8_locos.views.OnCardClickListener
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max
import kotlin.math.min

class MainActivity : AppCompatActivity(), OnCardClickListener {
    private var deck: MutableList<Card> = ArrayList()
    private var players:MutableList<Player> = ArrayList()
    private var cardsOnTable: MutableList<Card> = ArrayList()

    private var numPlayers: Int = 3

    private var turn: Int = 0

    private var activePunishment:Boolean = false
    private var cantPunishment:Int=0
    private var hayRepetida = false

    private var listName:MutableList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Recibo de data
        val name1 = intent.getBundleExtra("data")?.getString("namePlayer1").toString()
        val name2 = intent.getBundleExtra("data")?.getString("namePlayer2").toString()
        val name3 = intent.getBundleExtra("data")?.getString("namePlayer3")?.toString()
        //Agregar a la listaName
        this.listName.add(name1)
        this.listName.add(name2)

        if(name3 != null){
            this.listName.add(name3)
            this.numPlayers = 3
        }else{
            this.numPlayers = 2
            findViewById<TextView>(R.id.tviPlayer2).visibility = View.INVISIBLE
        }


        //Crear las 52 cartas
        this.createDeck()

        //Repartir cartas
        this.dealCards()

        //Definir primer jugador
        this.turn = (0 until this.numPlayers-1).random()
        findViewById<TextView>(R.id.tviCurrPlayer).text = this.players[this.turn].name

        //Colocar carta en la mesa
        this.placeCardOnTable()
        //Mostrar cartas del jugador actual
        this.showCards()
        //Mostrar num de cartas de los otros jugadores y del maso
        this.showRemainingCards()
        //Validar movimiento al iniciar
        if(!tieneMovimientos()){
            Toast.makeText(this,"Debe robar 1 carta", Toast.LENGTH_LONG).show()
            findViewById<Button>(R.id.btnDraw).setEnabled(true)
        }
        //Evento Pasar Turno
        findViewById<Button>(R.id.btnPass).setOnClickListener {_: View ->
            if(this.players[this.turn].hand.count()==1){
                val tempName = this.players[this.turn].name
                Toast.makeText(this,"El $tempName va por una", Toast.LENGTH_LONG).show()
            }
            if(this.turn < this.players.size - 1){
                this.turn += 1
            }else{
                this.turn = 0
            }
            //Evento mostrar turno
            val intent = Intent(this, NextplayerDialog::class.java)
            val bundle = Bundle()
            bundle.putString("player",this.players[this.turn].name)
            intent.putExtra("data",bundle)
            startActivity(intent)

            findViewById<TextView>(R.id.tviCurrPlayer).text = this.players[this.turn].name
            this.showCards()
            this.showRemainingCards()
            this.players[this.turn].setPages()
            //Desactivar Button
            findViewById<Button>(R.id.btnPass).setEnabled(false)
            //Validar movimiento al cambiar de player
            if(!tieneMovimientos()){
                if(!activePunishment) {
                    Toast.makeText(this, "Debe robar 1 carta", Toast.LENGTH_LONG).show()
                    findViewById<Button>(R.id.btnDraw).setEnabled(true)
                }else{
                    Toast.makeText(this, "Debe robar $cantPunishment cartas", Toast.LENGTH_LONG).show()
                    for(i in 0 until cantPunishment){
                        this.players[this.turn].hand.add(this.deck.removeLast())
                    }
                    this.players[this.turn].setPages()
                    this.showCards()
                    this.showRemainingCards()
                    activePunishment=false
                    cantPunishment=0
                    if(!tieneMovimientos()){
                        Toast.makeText(this, "Debe robar 1 carta", Toast.LENGTH_LONG).show()
                        findViewById<Button>(R.id.btnDraw).setEnabled(false)
                        findViewById<Button>(R.id.btnPass).setEnabled(true)
                    }
                }
            }else{
                if(activePunishment){
                    findViewById<Button>(R.id.btnDraw).setEnabled(true)
                }else{
                    findViewById<Button>(R.id.btnDraw).setEnabled(false)
                }
            }
        }

        // Evento Ordenar
        findViewById<Button>(R.id.btnSort).setOnClickListener {_: View ->
            this.players[this.turn].hand = this.sortCards(this.players[this.turn].hand).toMutableList()
            this.showCards()
        }

        // Evento Página anterior
        findViewById<Button>(R.id.btnPrevPage).setOnClickListener{_: View ->
            this.players[this.turn].decreasePage()
            this.showCards()
        }

        // Evento Página siguiente
        findViewById<Button>(R.id.btnNextPage).setOnClickListener{_: View ->
            this.players[this.turn].increasePage()
            this.showCards()
        }

        // Evento Robar una carta
        findViewById<Button>(R.id.btnDraw).setOnClickListener {_: View ->
            if(this.deck.isEmpty()) {
                Toast.makeText(this,"Baraja vacía. Volviendo a barajar cartas en la mesa...", Toast.LENGTH_LONG).show()
                this.deck.addAll(this.cardsOnTable)
                this.cardsOnTable = ArrayList()
            }
            findViewById<Button>(R.id.btnDraw).setEnabled(false)
            if(activePunishment){
                for(i in 0 until cantPunishment){
                    this.players[this.turn].hand.add(this.deck.removeLast())
                }
                this.players[this.turn].setPages()
                this.showCards()
                this.showRemainingCards()
                activePunishment=false
                cantPunishment=0
            }else {
                // Agregar card
                this.players[this.turn].hand.add(this.deck.removeLast())
                this.players[this.turn].setPages()
                this.showCards()
                this.showRemainingCards()
            }
            findViewById<Button>(R.id.btnPass).setEnabled(true)
        }
    }

    private fun placeCardOnTable() {
        val cardTable: CardView = findViewById(R.id.cardTable)

        val card = this.deck.removeLast();

        cardTable.setCard(card)

        this.cardsOnTable.add(card)
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
        // Cambiado a la catidad actual en mano
        val actualPage = currPlayer.getPage()


        for(i in 0..7){
            val resId = resources.getIdentifier("card${i+1}", "id", packageName)
            val card: CardView = findViewById(resId)
            if(i<actualPage.count()){
                card.visibility = View.VISIBLE
                card.setCard(actualPage[i])
                //OnClick en carta
                card.setOnCardClickListener(this)
            }else{
                card.visibility = View.INVISIBLE
            }
        }
    }

    private fun dealCards() {
        for (i in 1..numPlayers){
            val currHand: MutableList<Card> = ArrayList()
            for(j in 1..8){
                currHand.add(this.deck.removeLast())
            }
            this.players.add(Player(i,this.listName[i-1], currHand))
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

    override fun onClick(valor:String, suit:String) {
        //Logica de click en carta
        var cardTable:CardView = findViewById(R.id.cardTable)
        if(activePunishment){
            if(valor==cardTable.getValor()){
                jugarCarta(valor,suit)
            }else{
                Toast.makeText(this, "Puede aumentar el castigo o robar cartas", Toast.LENGTH_LONG).show()
            }
        }else {
            if(hayRepetida){
                if(valor==cardTable.getValor()){
                    hayRepetida = false
                    jugarCarta(valor,suit)
                }else{
                    Toast.makeText(this, "Puede tirar doble o pasar", Toast.LENGTH_LONG).show()
                }
            }else {
                if (suit == cardTable.getSuit()) {
                    jugarCarta(valor, suit)
                } else {
                    if (valor == cardTable.getValor()) {
                        jugarCarta(valor, suit)
                    }
                }
            }
        }
    }

    private fun jugarCarta(valor:String, suit:String){
        var cardTable:CardView = findViewById(R.id.cardTable)
        var numValor:Int
        when(valor){
            "K" -> numValor=13
            "Q" -> numValor=12
            "J" -> numValor=11
            "A" -> numValor=1
            else -> numValor = Integer.parseInt(valor)
        }
        var newCard = Card(numValor,suit)
        cardTable.setCard(newCard)
        this.cardsOnTable.add(newCard)
        val currPlayer = players[this.turn]
        currPlayer.hand.removeAt(currPlayer.buscarCarta(numValor, suit))
        // Accion Ganar
        if(currPlayer.hand.count()==0){
            endGame()
        }else {
            // Habilidades especiales
            specialCard(valor)
            if (!hayRepetida) {
                findViewById<Button>(R.id.btnPass).callOnClick()
            } else {
                // Volver a jugar con carta igual
                Toast.makeText(this,"Puedes jugar la carta repetida", Toast.LENGTH_LONG).show()

                findViewById<Button>(R.id.btnPass).setEnabled(true)
                this.showCards()
                this.showRemainingCards()
            }
        }
    }

    private fun endGame(){
        val tempName = players[this.turn].name
        Toast.makeText(this, "$tempName ha ganado", Toast.LENGTH_LONG).show()
        val intent = Intent(this, GanadorDialog::class.java)
        val bundle = Bundle()
        bundle.putString("ganador",tempName)
        intent.putExtra("data",bundle)
        startActivity(intent)
    }

    private fun specialCard(valor:String) {
        if (valor == "K") {
            activePunishment = true
            cantPunishment += 3
        } else if (valor == "Q") {
            // valor == Q +2 en cant no implementado por alcance
        } else if (valor == "J") {
            if (this.turn < this.players.size - 1) {
                this.turn += 1
            } else {
                this.turn = 0
            }
        } else {
            // Repetidas patito
            val currPlayer = players[this.turn]
            var numValor:Int
            when(valor){
                "A" -> numValor=1
                else -> numValor = Integer.parseInt(valor)
            }
            for (i in 0 until currPlayer.hand.size) {
                var currCard = currPlayer.hand[i]
                if (currCard.valor == numValor) {
                    hayRepetida = true
                    break
                }
            }
        }
    }


    private fun tieneMovimientos():Boolean {
        val cardTable: CardView = findViewById(R.id.cardTable)
        var mov=false
        val currPlayer = players[this.turn]
        if (activePunishment){
            for(i in 0 until currPlayer.hand.size){
                var currCard = currPlayer.hand[i]
                if (currCard.valor == 13){
                    mov=true
                }
            }
        }else{
            for(i in 0 until currPlayer.hand.size){
                var currCard = currPlayer.hand[i]
                if (currCard.suit == cardTable.getSuit()){
                    mov=true
                    break
                }else{
                    if (currCard.valor == cardTable.getValorNum()){
                        mov=true
                    }
                }
            }
        }
        return mov
    }

}