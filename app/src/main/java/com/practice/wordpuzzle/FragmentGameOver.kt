package com.practice.wordpuzzle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.practice.wordpuzzle.databinding.FragmentGameOverBinding


class FragmentGameOver : Fragment() {

    lateinit var binding: FragmentGameOverBinding
    lateinit var gameOverViewModel: GameOverViewModel
    lateinit var scoreViewModelFactory: ScoreViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_over, container, false)
        binding.btnPlayAgain.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_fragmentGameOver_to_titleFragment)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            val args = FragmentGameOverArgs.fromBundle(it)
            scoreViewModelFactory = ScoreViewModelFactory(args.score)
            gameOverViewModel = ViewModelProvider(this, scoreViewModelFactory).get(GameOverViewModel::class.java)
            gameOverViewModel.score.observe(viewLifecycleOwner, Observer {
                binding.txtScoreValue.text = it.toString()
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val callback = requireActivity().onBackPressedDispatcher.addCallback(this){
//
//        }
    }
}