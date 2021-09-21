package pe.edu.ulima.game_8_locos

import kotlin.math.min

class Player(val num: Int, val name: String, var hand: MutableList<Card>) {
    private var pages: Int? = null
    private var currPage: Int? = null
    init {
        this.pages = hand.count()/8 + if(hand.count().rem(8)>0) 1 else 0
        this.currPage = 0
    }

    public fun getPage(): List<Card>{
        return this.hand.subList(this.currPage!!*8, min((this.currPage!!*8)+8,this.hand.count()))
    }

    public fun increasePage(){
        if(this.currPage!! < this.pages?.minus(1)!!){
            this.currPage = this.currPage?.plus(1)
        }
    }

    public fun decreasePage(){
        if(this.currPage!! >0){
            this.currPage = this.currPage?.minus(1)
        }
    }

    public fun firstPage(){
        this.currPage = 0
    }

    public fun lastPage(){
        this.currPage = this.pages?.minus(1)
    }

    fun buscarCarta(valor: Int, suit: String):Int{
        var cod:Int = 0
        for(i in 0 until this.hand.size){
            println(i)
            if(this.hand[i].valor==valor && this.hand[i].suit==suit){
                cod = i
                break
            }
        }
        return cod
    }

    fun setPages(){
        if(hand.count()>8*4){
            this.pages=5
        }else if(hand.count()>8*3){
            this.pages=4
        }else if(hand.count()>8*2){
            this.pages=3
        }else if(hand.count()>8*1){
            this.pages=2
        }else{
            this.pages=1
        }

    }
}