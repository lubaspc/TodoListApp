package com.lubinpc.todolist.ui.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.lubinpc.todolist.R;
import com.lubinpc.todolist.databinding.ActivityListNotesBinding;
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

public class ListNotesActivity extends AppCompatActivity {
    private ActivityListNotesBinding vBind;
    private UserVM user = new UserVM();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vBind = DataBindingUtil.setContentView(this, R.layout.activity_list_notes);
        user.setUser("Lubin Perez");
        vBind.setUser(user);
        RecyclerUtils.INSTANCE
                .setupRecyclerView(vBind.rvNotes, this, new StaggeredGridLayoutManager(2,1));
        setupContent();
        setupBtnAdd();
        setupToolbar();
    }

    private void setupToolbar() {
        ToolbarUtils.INSTANCE.setupLogoutBtn(vBind.toolbar.toolbar,this,"");
    }

    private void setupBtnAdd() {
        vBind.fabAdd.setOnClickListener(v -> {
            DialogNote dialog = new DialogNote(new NoteVM(),new DialogNote.onActionDialog(){
                @Override
                public void save(NoteVM note) {

                }

                @Override
                public void destroy(NoteVM note) {}
            });
            dialog.show(getSupportFragmentManager(),DialogNote.TAG);
        });
    }

    private void setupContent() {
        List<NoteVM> notes = getNotes();
        vBind.setIsEmpty(notes.isEmpty());
        vBind.rvNotes.setAdapter(new NoteAdapter(notes, (note) -> {
            DialogNote dialog = new DialogNote(note,new DialogNote.onActionDialog(){
                @Override
                public void save(NoteVM note) {

                }

                @Override
                public void destroy(NoteVM note) {

                }
            });
            dialog.show(getSupportFragmentManager(),DialogNote.TAG);
            return null;
        }));
    }

    private List<NoteVM> getNotes() {
        List<NoteVM> notes = new ArrayList<>();
        for (int i=1; i<20; i++){
            NoteVM note = new NoteVM();
            note.setId((long) i);
            note.setTitle("Titulo numero "+i);
            note.setText("Text de cuerpo numero "+i);
            if (i%2 == 0){
                note.setProgramed((i+10)+"-10-2020");
                note.setProgramedTime(Calendar.getInstance().getTimeInMillis());
            }
            notes.add(note);
        }
        return notes;
    }

    @Override
    public boolean onSupportNavigateUp() {
        Dialogs.INSTANCE.alert(this,"¿Estas seguro cerrar sesión?",()-> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return  null;
        });
        return true;
    }
}