package com.practice.wordpuzzle

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.practice.wordpuzzle.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {

    lateinit var binding: FragmentTitleBinding
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)
        binding.btnPlay.setOnClickListener{
            val action = TitleFragmentDirections.actionTitleFragmentToFragmentPuzzle()
            view!!.findNavController().navigate(action)
        }
        return binding.root
    }

}