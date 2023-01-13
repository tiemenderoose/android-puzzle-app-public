package com.example.puzzleapp.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.puzzleapp.R
import com.example.puzzleapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        // show preview of image
        binding.imageViewPuzzlePreview.setImageResource(R.drawable.sample_bitmap)

        // Navigate to puzzle screen on button click
        binding.buttonStartPuzzle.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPuzzlePickerFragment())
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}
