package com.example.puzzleapp.screens.utils

import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.puzzleapp.R
import com.example.puzzleapp.domain.Puzzle
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import timber.log.Timber

@BindingAdapter("imageBitmap")
fun setImageViewResource(view: ImageView, bitmap: Bitmap?) {
    if (bitmap == null) {
        view.setImageResource(R.drawable.placeholder)
    } else {
        view.setImageBitmap(bitmap)
    }
}

@BindingAdapter("puzzle")
fun setImageViewResource(view: ImageView, puzzle: Puzzle?) {
    Timber.tag("binder").d(puzzle?.puzzleId.toString())
    if (puzzle == null) {
        view.setImageResource(R.drawable.placeholder)
    } else {
        Glide.with(view.context)
            .setDefaultRequestOptions(RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
            .load(puzzle.imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(view)
    }
}

@BindingAdapter("text_int")
fun setTextViewInt(view: TextView, int: Int) {
    view.text = int.toString()
}

@BindingAdapter("text_date")
fun setTextViewDate(view: TextView, localDateTimeString: String?) {
    if (localDateTimeString != null) {
        val ldt = LocalDateTime.parse(localDateTimeString)
        view.text = ldt.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}

@BindingAdapter("text_time")
fun setTextViewTime(view: TextView, localDateTimeString: String?) {
    if (localDateTimeString != null) {
        val ldt = LocalDateTime.parse(localDateTimeString).truncatedTo(ChronoUnit.SECONDS)
        view.text = ldt.format(DateTimeFormatter.ISO_LOCAL_TIME)
    }
}
