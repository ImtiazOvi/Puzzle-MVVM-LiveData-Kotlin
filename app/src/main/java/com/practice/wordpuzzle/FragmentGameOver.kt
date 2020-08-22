package com.practice.wordpuzzle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.practice.wordpuzzle.databinding.FragmentGameOverBinding


class FragmentGameOver : Fragment() {

    lateinit var binding: FragmentGameOverBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_over, container, false)
        return binding.root
    }

}