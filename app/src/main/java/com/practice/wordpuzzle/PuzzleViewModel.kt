package com.practice.wordpuzzle

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PuzzleViewModel: ViewModel(){
    val score = MutableLiveData<Int>()
    var word = MutableLiveData<WordPuzzleData>()
    val gameFinish = MutableLiveData<Boolean>()

    lateinit var words: ArrayList<WordPuzzleData>

    init {
        loadData();
        nextWord();
        score.value = 0
        gameFinish.value = false
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
            gameFinish.value = true
        }else{
            word.value = words.removeAt(0)
        }
    }

    fun onSkip(){
        score.value = (score.value)?.minus(1)
        nextWord()
    }
    fun onWrongAnswer(){
        score.value = (score.value)?.minus(1)
        nextWord()
    }
    fun onRightAnswer(){
        score.value = (score.value)?.plus(1)
        nextWord()
    }
    fun onGameOver(){
        gameFinish.value = false
    }

}