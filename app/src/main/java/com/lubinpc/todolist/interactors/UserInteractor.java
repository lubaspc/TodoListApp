package com.lubinpc.todolist.interactors;

import com.lubinpc.db.DBTodoList;
import com.lubinpc.db.dao.NoteDao;
import com.lubinpc.retrofit.api.CBDone;
import com.lubinpc.retrofit.api.CBGeneric;
import com.lubinpc.retrofit.api.CBSuccess;
import com.lubinpc.retrofit.models.UserWS;
import com.lubinpc.todolist.async.RxInstruction;
import com.lubinpc.todolist.models.UserVM;
import com.lubinpc.todolist.ui.auth.LoginActivity;
import com.lubinpc.todolist.ui.auth.RegisterActivity;
import com.lubinpc.todolist.ui.notes.ListNotesActivity;
import com.lubinpc.todolist.utils.CPConfig;
import com.lubinpc.todolist.utils.ModelExtencion;
import com.lubinpc.retrofit.volley.RequestVolley;

public class UserInteractor implements LoginActivity.UserHandle, RegisterActivity.UserHandle, ListNotesActivity.UserHandle {

    private static UserInteractor instance;
    private NoteDao noteDao;

    public UserInteractor(){
        noteDao = DBTodoList.db.noteDao();
    }

    public static UserInteractor getInstance() {
        if (instance == null) instance = new UserInteractor();
        return instance;
    }


    @Override
    public void getActive(CBGeneric<UserVM> cb) {
        String userName = CPConfig.INSTANCE.getUser();
        if (userName.equals("")) {
            cb.onResult(null);
            return;
        }
        UserVM user = new UserVM();
        user.setUser(userName);
        cb.onResult(user);
    }

    @Override
    public void logout(CBDone cb) {
        CPConfig.INSTANCE.setToken("");
        CPConfig.INSTANCE.setUser("");
        new RxInstruction()
                .completable(noteDao::deleteAll)
                .exec(cb);
    }

    @Override
    public void login(UserVM userVM, CBSuccess<String> cb) {
        UserWS userWS = ModelExtencion.INSTANCE.getToUserWS(userVM);
        RequestVolley.getInstance().login(userWS,result -> {
            if (!result.isSuccess()){
                cb.onResponse(false,result.getMessage());
                return;
            }
            CPConfig.INSTANCE.setToken(result.getData().getApiToken());
            CPConfig.INSTANCE.setUser(result.getData().getUser());
            cb.onResponse(true,"Completo");
        });
    }

    @Override
    public void register(UserVM userVM, CBSuccess<String> cb) {
        UserWS userWS = ModelExtencion.INSTANCE.getToUserWS(userVM);
        RequestVolley.getInstance().register(userWS,(success,message) -> {
            if (!success) cb.onResponse(false,message);
            else login(userVM,cb);
        });
    }
}
