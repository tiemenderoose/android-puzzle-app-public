package com.example.puzzleapp.screens.puzzle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airbnb.paris.Paris
import com.example.puzzleapp.R
import com.example.puzzleapp.databinding.FragmentPuzzleBinding
import com.example.puzzleapp.repository.PuzzleRepository
import timber.log.Timber

class PuzzleFragment : Fragment() {

    private lateinit var viewModel: PuzzleViewModel
    private lateinit var binding: FragmentPuzzleBinding
    private lateinit var imageViews: List<ImageView>
    private var markedImage: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_puzzle,
            container,
            false
        )

        val args = PuzzleFragmentArgs.fromBundle(requireArguments())

        // Get an instance of the appContext to setup the database in the repository
        val appContext = requireNotNull(this.activity).application
        val dataSource = PuzzleRepository.getInstance(appContext)

        // use a factory to pass the repository reference to the viewModel
        val viewModelFactory = PuzzleViewModelFactory(args.puzzleId, dataSource, appContext)
        viewModel = ViewModelProvider(this, viewModelFactory)[PuzzleViewModel::class.java]

        binding.puzzleViewModel = viewModel
        binding.lifecycleOwner = this

        initializeList()

        imageViews.forEachIndexed { index, imageView: ImageView ->
            imageView.setOnClickListener {
                viewModel.selectImage(index)
            }
        }

        viewModel.isFinished.observe(
            viewLifecycleOwner
        ) { isFinished ->
            if (isFinished) {
                findNavController()
                    .navigate(PuzzleFragmentDirections.actionPuzzleFragmentToPuzzleCompleteFragment(viewModel.puzzle.puzzleId))
            }
        }

        viewModel.selectedImage.observe(
            viewLifecycleOwner
        ) { selectedImage ->
            Timber.d("Selected image -> %d".format(selectedImage))
            if (selectedImage == null && markedImage != null) {
                unmarkImage(markedImage!!)
            } else if (selectedImage != null) {
                markImage(selectedImage)
            }
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause triggered")

        if (markedImage != null) {
            viewModel.deselectImage()
        }
    }

    private fun markImage(nr: Int) {
        Paris.style(imageViews[nr]).apply(R.style.MarkedImage)
        markedImage = nr
    }

    private fun unmarkImage(nr: Int) {
        Paris.style(imageViews[nr]).apply(R.style.Image)
        markedImage = null
    }

    private fun initializeList() {
        imageViews = listOf(
            binding.imageView1,
            binding.imageView2,
            binding.imageView3,
            binding.imageView4,
            binding.imageView5,
            binding.imageView6,
            binding.imageView7,
            binding.imageView8,
            binding.imageView9,
            binding.imageView10,
            binding.imageView11,
            binding.imageView12,
            binding.imageView13,
            binding.imageView14,
            binding.imageView15,
            binding.imageView16,
        )
    }
}
