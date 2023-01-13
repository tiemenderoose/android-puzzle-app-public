package com.example.puzzleapp.screens.puzzlePicker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.puzzleapp.databinding.PuzzlePickerItemBinding
import com.example.puzzleapp.domain.Puzzle

class PuzzleAdapter(private val clickListener: PuzzleListener) : ListAdapter<Puzzle, ViewHolder>(PuzzleDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
}

class ViewHolder(private val binding: PuzzlePickerItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: PuzzleListener, puzzle: Puzzle) {
        binding.clickListener = clickListener
        binding.puzzle = puzzle
        binding.pickerItemCheckmark.visibility = if (puzzle.completionDate == null) View.INVISIBLE else View.VISIBLE
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = PuzzlePickerItemBinding.inflate(layoutInflater, parent, false)
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

class PuzzleListener(val clickListener: (puzzle: Puzzle) -> Unit) {
    fun onClick(puzzle: Puzzle) = clickListener(puzzle)
}
