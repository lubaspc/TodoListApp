package com.lubinpc.todolist;

import android.app.Application;

import androidx.room.Room;

import com.lubinpc.db.DBTodoList;
import com.lubinpc.todolist.utils.CPConfig;
import com.lubinpc.todolist.volley.RequestVolley;

public class TodoListApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CPConfig.INSTANCE.setContext(this);
        RequestVolley.getInstance(this);

        DBTodoList.db = Room
                .databaseBuilder(this, DBTodoList.class, "todo_list.db")
                .fallbackToDestructiveMigration()
                .build();

    }
}
