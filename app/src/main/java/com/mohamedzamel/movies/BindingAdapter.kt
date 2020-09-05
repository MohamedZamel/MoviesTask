package com.mohamedzamel.movies

import androidx.databinding.BindingAdapter
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

