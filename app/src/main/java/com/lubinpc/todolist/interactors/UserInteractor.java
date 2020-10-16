package com.lubinpc.todolist.interactors;

import com.lubinpc.retrofit.ApiTodoList;
import com.lubinpc.retrofit.api.CBGeneric;
import com.lubinpc.retrofit.api.CBSuccess;
import com.lubinpc.retrofit.models.UserWS;
import com.lubinpc.todolist.models.UserVM;
import com.lubinpc.todolist.ui.auth.LoginActivity;
import com.lubinpc.todolist.ui.auth.RegisterActivity;
import com.lubinpc.todolist.utils.CPConfig;
import com.lubinpc.todolist.utils.ModelExtencion;
import com.lubinpc.todolist.volley.RequestVolley;
import com.lubinpc.todolist.utils.ModelExtencion.*;

public class UserInteractor implements LoginActivity.UserHandle, RegisterActivity.UserHandle {

    private static UserInteractor instance;

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
