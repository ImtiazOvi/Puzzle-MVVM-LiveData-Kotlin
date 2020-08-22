package com.practice.wordpuzzle

import android.os.Bundle
import android.provider.UserDictionary
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.practice.wordpuzzle.databinding.FragmentPuzzleBinding
import com.practice.wordpuzzle.databinding.FragmentTitleBinding
import kotlinx.android.synthetic.main.fragment_puzzle.*

class FragmentPuzzle : Fragment() {

    lateinit var binding: FragmentPuzzleBinding
    private lateinit var puzzleViewModel: PuzzleViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_puzzle, container, false)

        puzzleViewModel = ViewModelProvider(this).get(PuzzleViewModel::class.java)
        binding.btnOk.setOnClickListener {
            checkAnswer()
            updateScore()
            updateWord()
        }

        binding.btnSkip.setOnClickListener {
            puzzleViewModel.onSkip()
            updateScore()
            updateWord()
        }
        updateScore()
        updateWord()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    private fun gameOver() {
        val action = FragmentPuzzleDirections.actionFragmentPuzzleToFragmentGameOver(puzzleViewModel.score)
        findNavController().navigate(action)
    }

    fun updateWord() {
        binding.txtPuzzel1st.text = puzzleViewModel.word?.question_gap_1
        binding.txtPuzzel2.text = puzzleViewModel.word?.question_gap_2
    }

    fun updateScore() {
        binding.txtScore.text = puzzleViewModel.score?.toString()
    }

    fun checkAnswer() {
        if (puzzle_word.text.toString().toUpperCase() == puzzleViewModel.word?.correctAnswer) {
            puzzle_word.text = null
            puzzleViewModel.onRightAnswer()
            puzzleViewModel.nextWord()
        } else {
            puzzle_word.text = null
            puzzleViewModel.onWrongAnswer()
            puzzleViewModel.nextWord()
        }
    }


}