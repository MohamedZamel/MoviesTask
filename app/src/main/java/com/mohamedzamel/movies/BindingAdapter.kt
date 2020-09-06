package com.mohamedzamel.movies

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.textview.MaterialTextView
import com.mohamedzamel.movies.shared.toHandyString


/**
 *adding bindingAdapter to [MaterialTextView] to allow it to use
 */
@BindingAdapter("spannableText")
fun bindSpannableText(textView: MaterialTextView, string: List<String>) {
    val text = string.toHandyString()
    textView.text = text
}

/**
 * load image from Url into the requested [ImgaeView] with loading progressbar and load [R.drawable.ic_baseline_error_outline_24]
 */
@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        val circularProgressDrawable = CircularProgressDrawable(view.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        Glide.with(view.context)
            .load(imageUrl).placeholder(circularProgressDrawable)
            .error(R.drawable.ic_baseline_error_outline_24).fitCenter()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}