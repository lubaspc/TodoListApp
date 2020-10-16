package com.lubinpc.todolist.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonSyntaxException
import org.json.JSONObject
import java.util.*

fun Any.toJSON() = JSONObject(Gson().toJsonTree(this).toString())

fun <T> toModel(json: JsonElement, model: Class<T>) = try {
    Gson().fromJson(json, model)
} catch (e: JsonSyntaxException) {
    null
}

fun <T> toModel(json: String, model: Class<T>) = try {
    Gson().fromJson(json, model)
} catch (e: JsonSyntaxException) {
    null
}
