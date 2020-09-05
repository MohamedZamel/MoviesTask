package com.mohamedzamel.movies

import android.widget.ImageView
import androidx.databinding.BindingAdapter
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

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}