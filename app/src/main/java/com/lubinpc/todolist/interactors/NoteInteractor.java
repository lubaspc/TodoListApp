package com.lubinpc.todolist.interactors;

import com.lubinpc.retrofit.ApiTodoList;
import com.lubinpc.todolist.utils.CPConfig;

public class NoteInteractor {
    private static NoteInteractor instance;
    private final ApiTodoList api;

    public NoteInteractor(){
        api = ApiTodoList.getInstance();
        api.setApiToken(CPConfig.INSTANCE.getToken());
    }

    public static NoteInteractor getInstance() {
        if (instance == null) instance = new NoteInteractor();
        return instance;
    }
}
