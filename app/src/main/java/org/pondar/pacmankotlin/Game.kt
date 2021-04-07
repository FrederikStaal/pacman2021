package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.TextView
import android.widget.Toast
import java.util.ArrayList

class Game(private var context: Context, view: TextView, private var hiscoreView: TextView) {

    private var pointsView: TextView = view
    private var points: Int = 0

    var hiscore: Int = 0
    private var hiscoreText: String = hiscore.toString()

    //bitmap of the pacman
    var pacBitmap: Bitmap
    var pacx: Int = 0
    var pacy: Int = 0

    //did we initialize the coins?
    var coinsInitialized = false

    //the list of goldcoins - initially empty
    var coins = ArrayList<GoldCoin>()
    var coinBitmap: Bitmap

    // Ghost Bitmap
    var ghostBitmap: Bitmap
    var ghostX: Int = 0
    var ghostmaxX: Int = 0
    var ghostY: Int = 0
    var ghostmaxY: Int = 0
    var destGhostX: Int = 0
    var destGhostY: Int = 0

    //a reference to the gameview
    private var gameView: GameView? = null
    private var h: Int = 0
    private var w: Int = 0 //height and width of screen

    //The init code is called when we create a new Game class.
    //it's a good place to initialize our images.
    init {
        pacBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman)
        coinBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.coin)
        ghostBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ghost)
    }

    fun setGameView(view: GameView) {
        this.gameView = view
    }

    //TODO initialize goldcoins also here
    fun initializeGoldcoins() {
        //DO Stuff to initialize the array list with some coins.
        generateCoins()
        coinsInitialized = true
    }

    private fun getRandomNumberHeight(): Int {
        return ((100..(h - 100)).random())
    }

    private fun getRandomNumberWidth(): Int {
        return ((100..(w - 100)).random())
    }

    private fun generateCoins() {
        coins.add(GoldCoin(getRandomNumberWidth(), getRandomNumberHeight()))
        coins.add(GoldCoin(getRandomNumberWidth(), getRandomNumberHeight()))
        coins.add(GoldCoin(getRandomNumberWidth(), getRandomNumberHeight()))
        coins.add(GoldCoin(getRandomNumberWidth(), getRandomNumberHeight()))
        coins.add(GoldCoin(getRandomNumberWidth(), getRandomNumberHeight()))
        coins.add(GoldCoin(getRandomNumberWidth(), getRandomNumberHeight()))
        coins.add(GoldCoin(getRandomNumberWidth(), getRandomNumberHeight()))
        coins.add(GoldCoin(getRandomNumberWidth(), getRandomNumberHeight()))
        coins.add(GoldCoin(getRandomNumberWidth(), getRandomNumberHeight()))
        coins.add(GoldCoin(getRandomNumberWidth(), getRandomNumberHeight()))
    }


    fun newGame() {
        pacx = 50
        pacy = 400
        ghostX = 300
        ghostmaxX = ghostX + 100
        ghostY = 300
        ghostmaxY = ghostY + 100
        coins.removeAll(coins)
        coinsInitialized = false
        points = 0
        pointsView.text = "${context.resources.getString(R.string.points)} $points"
        gameView?.invalidate() //redraw screen
    }

    fun setSize(h: Int, w: Int) {
        this.h = h
        this.w = w
    }

    // Pacman Movement
    fun movePacmanLeft(pixels: Int) {
        if (pacx - pixels > 0) {
            pacx = pacx - pixels
            moveGhostLeft(10)
            doCollisionCheck()
            gameView!!.invalidate()
        }
    }

    fun movePacmanRight(pixels: Int) {
        //still within our boundaries?
        if (pacx + pixels + pacBitmap.width < w) {
            pacx = pacx + pixels
            moveGhostRight(10)
            doCollisionCheck()
            gameView!!.invalidate()
        }
    }

    fun movePacmanUp(pixels: Int) {
        if (pacy - pixels > 0) {
            pacy = pacy - pixels
            moveGhostUp(10)
            doCollisionCheck()
            gameView!!.invalidate()
        }
    }

    fun movePacmanDown(pixels: Int) {
        if (pacy + pixels + pacBitmap.height < h) {
            pacy = pacy + pixels
            moveGhostDown(10)
            doCollisionCheck()
            gameView!!.invalidate()
        }
    }

    // Ghost movement
    fun moveGhostUp(pixels: Int) {
        if (destGhostY - pixels > 0) {
            destGhostY -= pixels
        }
    }

    fun moveGhostDown(pixels: Int) {
        if (destGhostY + pixels + ghostBitmap.height < h) {
            destGhostY += pixels
        }
    }

    fun moveGhostLeft(pixels: Int) {
        if (destGhostX - pixels > 0) {
            destGhostX -= pixels
        }
    }

    fun moveGhostRight(pixels: Int) {
        if (destGhostX + pixels + ghostBitmap.width < w) {
            destGhostX += pixels
        }
    }

    //TODO check if the pacman touches a gold coin
    //and if yes, then update the neccesseary data
    //for the gold coins and the points
    //so you need to go through the arraylist of goldcoins and
    //check each of them for a collision with the pacman
    fun doCollisionCheck() {
        var pacCenterX = pacx + pacBitmap.width/2
        var pacCenterY = pacy + pacBitmap.height/2
        var coinMaxX = coinBitmap.width
        var coinMaxY = coinBitmap.height
        var ghostCenterX = ghostX + ghostBitmap.width/2
        var ghostCenterY = ghostY + ghostBitmap.height/2

        if (pacCenterX == ghostCenterX || pacCenterY == ghostCenterY) {
            Toast.makeText(gameView!!.context, "You died - Start new game", Toast.LENGTH_SHORT).show()
            updateHiscore()
        }

        for (GoldCoin in coins) {
            if (!GoldCoin.taken) {
                if (pacCenterX > GoldCoin.coinx && pacCenterX < coinMaxX || pacCenterY > GoldCoin.coiny && pacCenterY < coinMaxY) {
                    points += 10
                    GoldCoin.taken = true
                    updateScore()

                }
            }
        }
    }

    private fun updateScore() {
        pointsView.text = "${context.resources.getString(R.string.points)} $points"
    }

    fun winGame() {
        if (hiscore < points) {
            hiscore = points
            updateHiscore()
        }
        Toast.makeText(context, "Time ran out", Toast.LENGTH_SHORT).show()
    }

    private fun updateHiscore() {
        hiscoreText = "Hiscore: $hiscore"
        hiscoreView.text = hiscoreText
    }


}