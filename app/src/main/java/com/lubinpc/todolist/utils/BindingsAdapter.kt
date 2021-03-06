package com.lubinpc.todolist.utils

import android.media.Image
import android.opengl.Visibility
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lubinpc.todolist.R
import java.text.NumberFormat
import java.util.*

@BindingAdapter("visible")
fun View.bindVisible(visible: Boolean?) {
    visibility = if (visible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("loadUrl")
fun ImageView.loadUrl(url: String?) {
    if (url != null && url.isNotEmpty()) {
        Glide.with(this)
                .load(url)
                .into(this)
    }
}

@BindingAdapter("asMoney")
fun TextView.asMoney(price: Number?) {
    text = NumberFormat.getCurrencyInstance(Locale.CANADA).format(price ?: 0)
}

@BindingAdapter("textHtml")
fun TextView.textHtml(textI: String?) {
    text = Html.fromHtml(textI)
}

@BindingAdapter("checkedIcon")
fun FloatingActionButton.checked(check: Boolean?) {
    var idD = R.drawable.ic_baseline_radio_button_unchecked
    if (check == true)
        idD = R.drawable.ic_baseline_radio_button_checked
    setImageResource(idD)
}

@BindingAdapter("backgroundBool")
fun MaterialCardView.background(check: Boolean?) {
    setCardBackgroundColor(this.context.getColor(
            if (check == true) R.color.toolbar
            else R.color.note
    ))
}