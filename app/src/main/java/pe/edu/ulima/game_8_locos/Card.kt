package pe.edu.ulima.game_8_locos

class Card(val valor:Int, val suit:String) {

    override fun toString(): String {
        return "Suit: ${this.suit}, Value: ${this.valor}"
    }
}