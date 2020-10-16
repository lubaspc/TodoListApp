package com.lubinpc.todolist.utils

import com.google.gson.Gson
import com.lubinpc.retrofit.models.UserWS
import com.lubinpc.todolist.models.UserVM

object ModelExtencion{
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
}