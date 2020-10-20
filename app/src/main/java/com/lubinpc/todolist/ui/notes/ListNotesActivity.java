package com.lubinpc.todolist.ui.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.lubinpc.retrofit.api.CBDone;
import com.lubinpc.retrofit.api.CBGeneric;
import com.lubinpc.retrofit.api.CBSuccess;
import com.lubinpc.todolist.R;
import com.lubinpc.todolist.databinding.ActivityListNotesBinding;
import com.lubinpc.todolist.interactors.NoteInteractor;
import com.lubinpc.todolist.interactors.UserInteractor;
import com.lubinpc.todolist.models.NoteVM;
import com.lubinpc.todolist.models.UserVM;
import com.lubinpc.todolist.ui.auth.LoginActivity;
import com.lubinpc.todolist.ui.notes.adapter.NoteAdapter;
import com.lubinpc.todolist.utils.RecyclerUtils;
import com.lubinpc.todolist.utils.ToolbarUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mx.com.satoritech.womandriveandroidcliente.utils.Dialogs;
import mx.com.satoritech.womandriveandroidcliente.utils.ProgressDialog;

public class ListNotesActivity extends AppCompatActivity {
    private ActivityListNotesBinding vBind;
    private UserHandle userHandle;
    private NoteHandle noteHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userHandle = UserInteractor.getInstance();
        noteHandle = NoteInteractor.getInstance();
        vBind = DataBindingUtil.setContentView(this, R.layout.activity_list_notes);
        setupContent();
        getUserActive();
        RecyclerUtils.INSTANCE
                .setupRecyclerView(vBind.rvNotes, this,
                        new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        setupBtnAdd();
        setupToolbar();
    }

    private void getUserActive() {
        userHandle.getActive(vBind::setUser);
    }

    private void setupToolbar() {
        ToolbarUtils.INSTANCE.setupLogoutBtn(vBind.toolbar.toolbar,this,"Mis tareas");
    }

    private void setupBtnAdd() {
        vBind.fabAdd.setOnClickListener(v -> {
            DialogNote dialog = new DialogNote(new NoteVM(),new DialogNote.onActionDialog(){
                @Override
                public void save(NoteVM note) {
                    ProgressDialog progress = Dialogs.INSTANCE.progressDialog(getSupportFragmentManager());
                    noteHandle.creteNote(note,(success, message) -> {
                        progress.dismiss();
                        if (!success) Dialogs.INSTANCE.alert(ListNotesActivity.this,message);
                        else setupContent();
                    });
                }
                @Override
                public void destroy(NoteVM note) {}
            });
            dialog.show(getSupportFragmentManager(),DialogNote.TAG);
        });
    }

    private void setupContent() {
        verifyAlarmDay();
        ProgressDialog progress = Dialogs.INSTANCE.progressDialog(getSupportFragmentManager());
        noteHandle.getNotes(notes -> {
            progress.dismiss();
            if (notes == null){
                Dialogs.INSTANCE.alert(this,() -> {
                    startActivity(getIntent());
                    finish();
                    return null;
                });
                return;
            }
            vBind.setIsEmpty(notes.isEmpty());
            vBind.rvNotes.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            vBind.rvNotes.setAdapter(new NoteAdapter(notes, (note) -> {
                DialogNote dialog = new DialogNote(note.clone(),new DialogNote.onActionDialog(){
                    @Override
                    public void save(NoteVM note) {
                        ProgressDialog progress = Dialogs.INSTANCE.progressDialog(getSupportFragmentManager());
                        noteHandle.update(note,(success, message) -> {
                            progress.dismiss();
                            if (!success) Dialogs.INSTANCE.alert(ListNotesActivity.this,message);
                            else setupContent();
                        });
                    }

                    @Override
                    public void destroy(NoteVM note) {
                        ProgressDialog progress = Dialogs.INSTANCE.progressDialog(getSupportFragmentManager());
                        noteHandle.destroy(note.getId(),(success, message) -> {
                            progress.dismiss();
                            if (!success) Dialogs.INSTANCE.alert(ListNotesActivity.this,message);
                            else setupContent();
                        });
                    }
                });
                dialog.show(getSupportFragmentManager(),DialogNote.TAG);
                return null;
            }));
        });
        vBind.rvNotes.smoothScrollToPosition(0);
    }


    @Override
    public boolean onSupportNavigateUp() {
        Dialogs.INSTANCE.alert(this,"¿Estas seguro cerrar sesión?",()-> {
            userHandle.logout(() -> {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            });
            return  null;
        });
        return true;
    }

    private void verifyAlarmDay() {
        AlarmManager alarmManager =(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        noteHandle.getNextNote(notes -> {
            if (notes == null || notes.isEmpty())return;
            for(NoteVM note: notes){
                Intent intent = new Intent(this,LoginActivity.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
                long millis = note.getProgramedTime();
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,note.getProgramedTime(),AlarmManager.INTERVAL_DAY,pendingIntent);
            }
        });
    }

    public interface UserHandle{
        void getActive(CBGeneric<UserVM> cb);
        void logout(CBDone cb);
    }

    public interface NoteHandle{
        void getNotes(CBGeneric<List<NoteVM>> cb);
        void creteNote(NoteVM note, CBSuccess<String> cb);
        void update(NoteVM note, CBSuccess<String> cb);
        void destroy(long noteId, CBSuccess<String> cb);
        void getNextNote(CBGeneric<List<NoteVM>> cb);

    }
}