package com.lubinpc.todolist.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.lubinpc.retrofit.api.CBGeneric;
import com.lubinpc.retrofit.api.CBSuccess;
import com.lubinpc.todolist.R;
import com.lubinpc.todolist.interactors.NoteInteractor;
import com.lubinpc.todolist.models.NoteVM;
import com.lubinpc.todolist.ui.auth.LoginActivity;

import java.nio.file.attribute.AclEntry;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class NotificationService extends Service {
    private Disposable observer;
    private NoteHandle handle;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handle = NoteInteractor.getInstance();
        findTask();
        observer = Observable.interval(5, TimeUnit.MINUTES)
                .subscribe((observer) -> findTask());
        return START_STICKY;
    }

    private void findTask(){
        handle.getNextNote(result -> {
            if (result == null || result.isEmpty()) return;
            for (NoteVM note: result){
                sendNotification(note);
            }
        });
    }

    private void sendNotification(NoteVM note){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(), 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "TAREA_PRÓXIMA");
        builder.setContentTitle(note.getTitle());
        builder.setContentText(note.getText());
        builder.setSmallIcon(R.drawable.ic_baseline_assignment);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentIntent(pendingIntent);
        builder.setPriority(NotificationCompat.PRIORITY_MAX)  ;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel("TAREA_PRÓXIMA") == null) {
                NotificationChannel notificationChannel = new NotificationChannel(
                       "TAREA_PRÓXIMA",
                        "Nueva notificación",
                        NotificationManager.IMPORTANCE_HIGH
                );
                notificationChannel.setDescription("Notification channel");
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }

    public interface NoteHandle{
        void getNextNote(CBGeneric<List<NoteVM>> cb);
    }
}
