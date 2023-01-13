package com.example.puzzleapp.screens.puzzlePicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.puzzleapp.R
import com.example.puzzleapp.databinding.FragmentPuzzlePickerBinding
import com.example.puzzleapp.repository.PuzzleRepository
import com.google.android.material.snackbar.Snackbar

class PuzzlePickerFragment : Fragment() {

    private lateinit var viewModel: PuzzlePickerViewModel
    private lateinit var binding: FragmentPuzzlePickerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_puzzle_picker,
            container,
            false
        )

        binding.lifecycleOwner = this

        // Get an instance of the appContext to setup the database in the repository
        val appContext = requireNotNull(this.activity).application
        val dataSource = PuzzleRepository.getInstance(appContext)

        // use a factory to pass the repository reference to the viewModel
        val viewModelFactory = PuzzlePickerViewModelFactory(dataSource, appContext)
        viewModel = ViewModelProvider(this, viewModelFactory)[PuzzlePickerViewModel::class.java]

        val adapter = PuzzleAdapter(
            PuzzleListener {
                puzzle ->
                findNavController()
                    .navigate(PuzzlePickerFragmentDirections.actionPuzzlePickerFragmentToPuzzleFragment(puzzle.puzzleId))
            }
        )
        binding.pickerRecyclerView.adapter = adapter

        // if the viewmodel's list changes, submit this list to the recycler's adapter
        // also set the visibility of the clear button based on the presence of puzzles in the list
        viewModel.puzzleList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.fabClearUnfinished.visibility = if (it.isNotEmpty()) View.VISIBLE else View.INVISIBLE
        }

        // always scroll to the newly added item in the RecyclerView
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(position: Int, itemCount: Int) {
                binding.pickerRecyclerView.scrollToPosition(position)
            }
        })

        // if a request to create a puzzle fails, show a snackbar with a retry option
        viewModel.requestFailed.observe(viewLifecycleOwner) {
            if (it) {
                Snackbar.make(binding.fabClearUnfinished, R.string.request_failed, Snackbar.LENGTH_LONG)
                    .setAction(R.string.request_retry) {
                        viewModel.addPuzzle()
                    }.show()
                viewModel.showSnackbarComplete()
            }
        }

        binding.fabAddPuzzle.setOnClickListener {
            viewModel.addPuzzle()
        }

        // show an extra confirmation for the clearing of all the unfinished puzzles
        binding.fabClearUnfinished.setOnClickListener {
            Snackbar.make(binding.fabClearUnfinished, R.string.clear_unfinished_confirmation, Snackbar.LENGTH_LONG)
                .setAction(R.string.clear_confirm) {
                    viewModel.clearUnfinishedPuzzles()
                }.show()
        }

        return binding.root
    }
}
