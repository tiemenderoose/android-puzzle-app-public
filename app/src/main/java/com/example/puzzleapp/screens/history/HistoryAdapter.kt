package com.example.puzzleapp.screens.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.puzzleapp.databinding.HistoryItemBinding
import com.example.puzzleapp.domain.Puzzle

class HistoryAdapter : ListAdapter<Puzzle, ViewHolder>(PuzzleDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
}

class ViewHolder(private val binding: HistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(puzzle: Puzzle) {
        binding.puzzle = puzzle
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = HistoryItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}

class PuzzleDiffCallback : DiffUtil.ItemCallback<Puzzle>() {
    override fun areItemsTheSame(oldPuzzle: Puzzle, newPuzzle: Puzzle): Boolean {
        return oldPuzzle.puzzleId == newPuzzle.puzzleId
    }

    override fun areContentsTheSame(oldPuzzle: Puzzle, newPuzzle: Puzzle): Boolean {
        return oldPuzzle == newPuzzle
    }
}
