package com.example.puzzleapp.screens.puzzleComplete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.puzzleapp.R
import com.example.puzzleapp.databinding.FragmentPuzzleCompleteBinding
import com.example.puzzleapp.repository.PuzzleRepository

class PuzzleCompleteFragment : Fragment() {

    private lateinit var binding: FragmentPuzzleCompleteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_puzzle_complete,
            container,
            false
        )

        binding.lifecycleOwner = this

        val args = PuzzleCompleteFragmentArgs.fromBundle(requireArguments())

        // Get an instance of the appContext to setup the database in the repository
        val appContext = requireNotNull(this.activity).application
        val dataSource = PuzzleRepository.getInstance(appContext)

        // use a factory to pass the repository reference to the viewModel
        val viewModelFactory = PuzzleCompleteViewModelFactory(args.puzzleId, dataSource, appContext)
        binding.viewModel = ViewModelProvider(this, viewModelFactory)[PuzzleCompleteViewModel::class.java]

        binding.buttonReturn.setOnClickListener {
            findNavController().popBackStack()
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}
