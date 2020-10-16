package com.lubinpc.todolist.utils

import android.content.Context

object CPConfig {
    lateinit var context: Context

    private const val SP_KEY = "config_todo_list"

    val sharedPreferences
        get() = context.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE)

    private const val KEY_TOKEN = "key_token"
    var token: String
        get() =
            sharedPreferences
                    .getString(KEY_TOKEN, "") ?: ""
        set(value) {
            sharedPreferences
                    .edit()
                    .putString(KEY_TOKEN, value)
                    .apply()
        }

    private const val USER = "user_name"
    var user: String
        get() =
            sharedPreferences
                    .getString(USER, "") ?: ""
        set(value) {
            sharedPreferences
                    .edit()
                    .putString(USER, value)
                    .apply()
        }

}