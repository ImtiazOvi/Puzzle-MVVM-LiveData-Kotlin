package com.practice.wordpuzzle

import android.os.Bundle
import android.provider.UserDictionary
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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
        // bind lifeCycle owner
        binding.lifecycleOwner = this

        puzzleViewModel.word.observe(viewLifecycleOwner, Observer {
            binding.txtPuzzel1st.text = puzzleViewModel.word.value?.question_gap_1
            binding.txtPuzzel2.text = puzzleViewModel.word.value?.question_gap_2
        })
        // observer
        puzzleViewModel.score.observe(viewLifecycleOwner, Observer {
            binding.txtScore.text = it?.toString()

        })
        puzzleViewModel.gameFinish.observe(viewLifecycleOwner, Observer {
            if (it) {
                gameOver()
            }
        })

        binding.btnOk.setOnClickListener {
            checkAnswer()
        }

        binding.btnSkip.setOnClickListener {
            puzzleViewModel.onSkip()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    private fun gameOver() {
        val action = FragmentPuzzleDirections.actionFragmentPuzzleToFragmentGameOver(
            puzzleViewModel.score.value ?: 0
        )
        findNavController().navigate(action)
        puzzleViewModel.onGameOver()
     }


    fun checkAnswer() {
        if (puzzle_word.text.toString().toUpperCase() == puzzleViewModel.word.value?.correctAnswer
        ) {
            puzzle_word.text = null
            puzzleViewModel.onRightAnswer()
        } else {
            puzzle_word.text = null
            puzzleViewModel.onWrongAnswer()
         }
    }


}