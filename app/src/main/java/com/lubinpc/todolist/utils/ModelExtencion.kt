package com.lubinpc.todolist.utils

import com.google.gson.Gson
import com.lubinpc.db.models.NoteDB
import com.lubinpc.retrofit.models.NoteWS
import com.lubinpc.retrofit.models.UserWS
import com.lubinpc.todolist.models.NoteVM
import com.lubinpc.todolist.models.UserVM
import java.text.SimpleDateFormat
import java.util.*

object ModelExtencion {

    private val gson = Gson()

    private fun <T> Any.toModel(model: Class<T>): T =
            gson.fromJson(gson.toJsonTree(this), model)

    private fun <T> List<Any>.toListModel(model: Class<T>): List<T> {
        val list = mutableListOf<T>()
        this.forEach {
            list.add(it.toModel(model))
        }
        return list
    }

    val UserWS.toUserVM: UserVM
        get() = toModel(UserVM::class.java)

    val UserVM.toUserWS: UserWS
        get() = toModel(UserWS::class.java)

    val List<NoteWS>.toListNoteVM: List<NoteVM>
        get() = toListModel(NoteVM::class.java)

    val NoteVM.toNoteWS: NoteWS
        get() = toModel(NoteWS::class.java)

    val List<NoteWS>.toListNoteDB: List<NoteDB>
        get() {
            val list = mutableListOf<NoteDB>()
            for (it in this) {
                list.add(NoteDB().apply {
                    this.title = it.title
                    this.text = it.text
                    this.programed = SimpleDateFormat("dd-M-yyyy HH:mm").parse(it.programed)
                    this.id = it.id
                })
            }
            return list
        }

    val NoteWS.toNoteDB: NoteDB
        get() = NoteDB().apply {
            this.title = this@toNoteDB.title
            this.text = this@toNoteDB.text
            this.programed = SimpleDateFormat("dd-M-yyyy HH:mm").parse(this@toNoteDB.programed)
            this.id = this@toNoteDB.id
        }

    val List<NoteDB>.toDBListNoteVM: List<NoteVM>
        get() {
            val list = mutableListOf<NoteVM>()
            this.forEach {
                list.add(NoteVM().apply {
                    this.title = it.title
                    this.text = it.text
                    this.programedTime = it.programed.time
                    this.programed = CalendarUtils.fullFormat(Calendar.getInstance().apply {
                        time = it.programed
                    })
                })
            }
            return list
        }

    val NoteDB.toNoteVM: NoteVM
        get() = NoteVM().apply {
            this.title = this@toNoteVM.title
            this.text = this@toNoteVM.text
            this.programedTime = this@toNoteVM.programed.time
            this.programed = CalendarUtils.fullFormat(Calendar.getInstance().apply {
                time = this@toNoteVM.programed
            })
        }
}