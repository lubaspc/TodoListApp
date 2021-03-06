package com.lubinpc.todolist;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.room.Room;

import com.lubinpc.db.DBTodoList;
import com.lubinpc.db.models.NoteDB;
import com.lubinpc.retrofit.api.CBGeneric;
import com.lubinpc.todolist.interactors.NoteInteractor;
import com.lubinpc.todolist.models.NoteVM;
import com.lubinpc.todolist.service.NotificationService;
import com.lubinpc.todolist.ui.auth.LoginActivity;
import com.lubinpc.todolist.utils.CPConfig;
import com.lubinpc.retrofit.volley.RequestVolley;

import java.util.Calendar;
import java.util.List;

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

        startService();
    }

    private void startService() {
        try {
            Intent service = new Intent(this, NotificationService.class);
            stopService(service);
            startService(service);
        } catch (Exception e) {
            Log.e("Ocurrió un error", e.getMessage());
        }

    }


}
