package com.lubinpc.todolist.interactors;

import com.lubinpc.db.DBTodoList;
import com.lubinpc.db.dao.NoteDao;
import com.lubinpc.db.models.NoteDB;
import com.lubinpc.retrofit.ApiTodoList;
import com.lubinpc.retrofit.api.CBGeneric;
import com.lubinpc.retrofit.api.CBSuccess;
import com.lubinpc.retrofit.models.NoteWS;
import com.lubinpc.todolist.TodoListApp;
import com.lubinpc.todolist.async.RxInstruction;
import com.lubinpc.todolist.models.NoteVM;
import com.lubinpc.todolist.service.NotificationService;
import com.lubinpc.todolist.ui.notes.ListNotesActivity;
import com.lubinpc.todolist.utils.CPConfig;
import com.lubinpc.todolist.utils.CalendarUtils;
import com.lubinpc.todolist.utils.ModelExtencion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NoteInteractor implements ListNotesActivity.NoteHandle, NotificationService.NoteHandle {
    private static NoteInteractor instance;
    private final ApiTodoList api;
    private NoteDao noteDao;

    public NoteInteractor() {
        noteDao = DBTodoList.db.noteDao();
        api = ApiTodoList.getInstance();
        api.setApiToken(CPConfig.INSTANCE.getToken());
    }

    public static NoteInteractor getInstance() {
        if (instance == null) instance = new NoteInteractor();
        return instance;
    }

    @Override
    public void getNotes(CBGeneric<List<NoteVM>> cb) {
        api.setApiToken(CPConfig.INSTANCE.getToken());
        api.notes(null, (success, result) -> {
            if (!success) {
                cb.onResult(null);
                return;
            }
            cb.onResult(ModelExtencion.INSTANCE.getToListNoteVM(result));
            new RxInstruction()
                    .completable(() -> {
                        List<NoteDB> notesDB = new ArrayList<>();
                        for (NoteWS note: result){
                            if (note.getProgramedTime() != null) notesDB.add(
                                    ModelExtencion.INSTANCE.getToNoteDB(note)
                            );
                        }
                        noteDao.refresh(notesDB);
                    })
                    .exec(() -> {});
        });
    }

    @Override
    public void creteNote(NoteVM note, CBSuccess<String> cb) {
        api.create(ModelExtencion.INSTANCE.getToNoteWS(note), (success, result) -> {
            if (!success) cb.onResponse(false, "Ocurrió un error en la comunicación");
            else cb.onResponse(result.isSuccess(), result.getMessage());
        });
    }

    @Override
    public void update(NoteVM note, CBSuccess<String> cb) {
        api.update(note.getId(), ModelExtencion.INSTANCE.getToNoteWS(note), (success, result) -> {
            if (!success) cb.onResponse(false, "Ocurrió un error en la comunicación");
            else cb.onResponse(result.isSuccess(), result.getMessage());
        });
    }

    @Override
    public void destroy(long noteId, CBSuccess<String> cb) {
        api.destroy(noteId, (success, result) -> {
            if (!success) cb.onResponse(false, "Ocurrió un error en la comunicación");
            else cb.onResponse(result.isSuccess(), result.getMessage());
        });
    }

    @Override
    public void getNextNote(CBGeneric<List<NoteVM>> cb) {
        Calendar initDay = Calendar.getInstance();

        Calendar finalDay = Calendar.getInstance();
        finalDay.add(Calendar.MINUTE, 5);

        new RxInstruction()
                .single(noteDao::getAll)
                .exec(result -> {
                    if (result == null){
                        cb.onResult(null);
                        return;
                    }
                    List<NoteVM> notesVM = new ArrayList<>();
                    for (NoteDB noteDB : (List<NoteDB>) result){
                        if (noteDB.getProgramed().after(initDay.getTime()) &&
                                noteDB.getProgramed().before(finalDay.getTime())){
                            notesVM.add(
                                    ModelExtencion.INSTANCE.getToNoteVM(noteDB)
                            );
                        }

                    }
                    cb.onResult(notesVM);
                });
    }
}
