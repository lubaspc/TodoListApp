package com.lubinpc.todolist.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.SerializedName
import com.lubinpc.todolist.BR

class NoteVM : BaseObservable() {
    var id: Long? = 0

    @get:Bindable
    var title: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    @get:Bindable
    var text: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.text)
        }

    var programed: String? = ""
    @SerializedName("programed_time")
    var programedTime: Long? = 0

    @get:Bindable
    var complete: Boolean? = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.complete)
        }

    fun clone(): NoteVM {
       return NoteVM().apply {
           this.id = this@NoteVM.id
           this.title = this@NoteVM.title
           this.text = this@NoteVM.text
           this.programed = this@NoteVM.programed
           this.programedTime = this@NoteVM.programedTime
           this.complete = this@NoteVM.complete
       }
    }
}