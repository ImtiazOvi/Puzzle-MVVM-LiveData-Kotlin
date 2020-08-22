package com.practice.wordpuzzle

import android.util.Log
import androidx.lifecycle.ViewModel

class PuzzleViewModel: ViewModel(){
    var score = 0
    var word:WordPuzzleData? = null
    lateinit var words: ArrayList<WordPuzzleData>

    init {
        loadData();
        nextWord();
    }

    private fun loadData(){
        words = arrayListOf(
            WordPuzzleData("BOT","LE","T"),
            WordPuzzleData("EX","EFTION","C"),
            WordPuzzleData("PROCA","TINATION","S"),
            WordPuzzleData("INF","LTRATE","I"),
            WordPuzzleData("REC","NCILE","O")
        )
        words.shuffle()
    }

     fun nextWord(){
        if (words.isEmpty()){
           // gameOver()
        }else{
            word = words.removeAt(0)
        }
    }

    fun onSkip(){
        score--
        nextWord()
    }
    fun onWrongAnswer(){
        score--
        nextWord()
    }
    fun onRightAnswer(){
        score++
        nextWord()
    }

}