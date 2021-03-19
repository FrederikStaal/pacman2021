package org.pondar.pacmankotlin

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    //reference to the game class.
    private var game: Game? = null

    // Timer object
    private var gameTimer: Timer = Timer()

    private val RIGHT = 1
    private val DOWN = 2
    private val LEFT = 3
    private val UP = 4

    private var counter : Int = 0
    private var running = false
    private var direction = RIGHT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //makes sure it always runs in portrait mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        startButton.setOnClickListener(this)
        stopButton.setOnClickListener(this)
        resetButton.setOnClickListener(this)
        running = true

        gameTimer.schedule(object : TimerTask() {
            override fun run() {
                timerMethod()
            }
        }, 0, 200)

        game = Game(this,pointsView)

        //intialize the game view clas and game class
        game?.setGameView(gameView)
        gameView.setGame(game)
        game?.newGame()

        moveLeft.setOnClickListener {
            //game?.movePacmanLeft(10)
            direction = LEFT
        }
        moveRight.setOnClickListener {
            //game?.movePacmanRight(10)
            direction = RIGHT
        }
        moveUp.setOnClickListener {
            //game?.movePacmanUp(10)
            direction = UP
        }
        moveDown.setOnClickListener {
            //game?.movePacmanDown(10)
            direction = DOWN
        }
    }

    override fun onStop() {
        super.onStop()
        gameTimer.cancel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_settings) {
            Toast.makeText(this, "settings clicked", Toast.LENGTH_LONG).show()
            return true
        } else if (id == R.id.action_newGame) {
            Toast.makeText(this, "New Game clicked", Toast.LENGTH_LONG).show()
            game?.newGame()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //if anything is pressed - we do the checks here
    override fun onClick(v: View) {
        if (v.id == R.id.startButton) {
            running = true
        } else if (v.id == R.id.stopButton) {
            running = false
        } else if (v.id == R.id.resetButton) {
            counter = 0
            //gameView.reset() //you should call the newGame method instead of this
            game!!.newGame()
            running = false
            //textView.text = getString(R.string.timerValue,counter)

        }
    }

    private fun timerMethod() {
        this.runOnUiThread(timerTick)
    }

    private val timerTick = Runnable {
        if (running) {
            counter++

            val textView = ""
            //timeView.text = getString(R.string.timerValue, game!!.counter)
            timeView.text = (counter/5).toString()

            if (direction == RIGHT) {
                game!!.movePacmanRight(20)
            }
            else if (direction == DOWN) {
                game!!.movePacmanDown(20)
            }
            else if (direction == LEFT) {
                game!!.movePacmanLeft(20)
            }
            else if (direction == UP) {
                game!!.movePacmanUp(20)
            }

        }
    }
}
