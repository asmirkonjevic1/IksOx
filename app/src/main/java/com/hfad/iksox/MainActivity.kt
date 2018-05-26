package com.hfad.iksox

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @SuppressLint("SetTextI18n")
    fun dropIn(view : View){
        //0 is X and 1 is O
        val counter = view as ImageView

        //get Tag
        val tag = counter.tag.toString().toInt()

        //check if field in array is 2(free) and if game is active
        if (gameState[tag] == 2 && gameActive) {
            //set active player tag in array and increment drawCounter
            gameState[tag] = activePlayer
            drawCounter++

            //Set Alpha to 0
            counter.alpha = 0f

            //Update Board
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.ic_x)
                activePlayer = 1
            } else {
                counter.setImageResource(R.drawable.ic_o)
                activePlayer = 0
            }
            //Animate Counter
            counter.animate().alpha(1f).duration = 500

            //See if some1 won and display Toast
            winningPositions.forEach { ints: IntArray ->
                if (gameState[ints[0]] == gameState[ints[1]] && gameState[ints[1]] == gameState[ints[2]] && gameState[ints[0]] != 2) {

                    //Stop the game
                    gameActive = false

                    //Check who won and set string to X or O
                    if (activePlayer == 1) {
                        winner = "X"
                    } else {
                        winner = "O"
                    }
                    //Toast.makeText(this, "$winner has won!", Toast.LENGTH_LONG).show()

                    //display winner and display play again button
                    tv_winner.text = "$winner has won!"
                    tv_winner.visibility = View.VISIBLE
                    btn_playAgain.visibility = View.VISIBLE

                    //check for Draw
                }else if (drawCounter == 9 && winner == ""){
                    gameActive = false
                    tv_winner.text = "Draw"
                    tv_winner.visibility = View.VISIBLE
                    btn_playAgain.visibility = View.VISIBLE
                }
            }

        }
    }

    fun playAgain(view : View){
        //Hide textview and button
        tv_winner.visibility = View.INVISIBLE
        btn_playAgain.visibility = View.INVISIBLE

        //reset game variables
        activePlayer = 0
        gameActive = true
        gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)
        winner = ""
        drawCounter = 0

        //reset UI
        for (item in 0..gridLayout.childCount){
            val counter = gridLayout.getChildAt(item) as? ImageView
            counter?.setImageDrawable(null)
        }

    }

    companion object {
        var activePlayer = 0
        val winningPositions = arrayOf(intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8), intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8), intArrayOf(0, 4, 8), intArrayOf(2, 4, 6))
        var gameState : IntArray = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)
        var gameActive = true
        var winner: String = ""
        var drawCounter = 0
    }
}
