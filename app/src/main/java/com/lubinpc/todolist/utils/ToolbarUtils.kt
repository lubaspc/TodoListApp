package com.lubinpc.todolist.utils

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.lubinpc.todolist.R

object ToolbarUtils {

    fun setupBackBtn(toolbar: Toolbar, activity: AppCompatActivity,title: String = "") {
        activity.setSupportActionBar(toolbar)
        toolbar.findViewById<MaterialTextView>(R.id.tv_title).text = title
        val drawable = activity.getDrawable(R.drawable.ic_baseline_arrow_back).apply {
            this!!.setColorFilter(activity.getColor(R.color.black), PorterDuff.Mode.SRC_ATOP)
        }
        with(activity.supportActionBar){
            this?.setHomeAsUpIndicator(drawable)
            this?.setDisplayHomeAsUpEnabled(true)
            this?.setDisplayShowHomeEnabled(true)
            this?.setDisplayShowTitleEnabled(false)
        }
    }

    fun setupLogoutBtn(toolbar: Toolbar, activity: AppCompatActivity,title: String = "") {
        activity.setSupportActionBar(toolbar)
        toolbar.findViewById<MaterialTextView>(R.id.tv_title).text = title
        val drawable = activity.getDrawable(R.drawable.ic_baseline_exit_to_app).apply {
            this!!.setColorFilter(activity.getColor(R.color.black), PorterDuff.Mode.SRC_ATOP)
        }
        with(activity.supportActionBar){
            this?.setHomeAsUpIndicator(drawable)
            this?.setDisplayHomeAsUpEnabled(true)
            this?.setDisplayShowHomeEnabled(true)
            this?.setDisplayShowTitleEnabled(false)
        }
    }

    fun changeTitle(toolbar: Toolbar,title:String){
        toolbar.findViewById<MaterialTextView>(R.id.tv_title).text = title
    }


}