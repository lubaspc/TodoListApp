package com.lubinpc.todolist.utils

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView


object RecyclerUtils {

    fun RecyclerView.setupRecyclerView(
        ctx: Context,
        layout: RecyclerView.LayoutManager = LinearLayoutManager(ctx)
    ) {
        this.itemAnimator = DefaultItemAnimator()
        this.layoutManager = layout
    }

}