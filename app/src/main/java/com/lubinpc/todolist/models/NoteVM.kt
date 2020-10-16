package com.lubinpc.todolist.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
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
    var programedTime: Long? = 0
}