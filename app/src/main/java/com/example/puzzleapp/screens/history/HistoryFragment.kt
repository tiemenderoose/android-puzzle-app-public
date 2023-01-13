package com.example.puzzleapp.screens.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.puzzleapp.R
import com.example.puzzleapp.databinding.FragmentHistoryBinding
import com.example.puzzleapp.repository.PuzzleRepository
import com.google.android.material.snackbar.Snackbar

class HistoryFragment : Fragment() {

    private lateinit var viewModel: HistoryViewModel
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_history,
            container,
            false
        )

        binding.lifecycleOwner = this

        // Get an instance of the appContext to setup the database in the repository
        val appContext = requireNotNull(this.activity).application
        val dataSource = PuzzleRepository.getInstance(appContext)

        // use a factory to pass the repository reference to the viewModel
        val viewModelFactory = HistoryViewModelFactory(dataSource, appContext)
        viewModel = ViewModelProvider(this, viewModelFactory)[HistoryViewModel::class.java]

        val adapter = HistoryAdapter()
        binding.historyRecyclerView.adapter = adapter

        // observe the viewmodel's list for changes to send to the recyclerview
        // also changes the visibility of the clear button accordingly
        viewModel.puzzleList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.fabClearHistory.visibility = if (it.isNotEmpty()) View.VISIBLE else View.INVISIBLE
        }

        // show an extra confirmation for the clearing of the history
        binding.fabClearHistory.setOnClickListener {
            Snackbar.make(binding.fabClearHistory, R.string.clear_history_confirmation, Snackbar.LENGTH_LONG)
                .setAction(R.string.clear_confirm) {
                    viewModel.clearHistory()
                }.show()
        }

        return binding.root
    }
}
