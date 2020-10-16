package com.lubinpc.todolist.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.lubinpc.todolist.BR;

public class UserVM extends BaseObservable {
    private String user;
    private String password;
    private String apiToken;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        notifyPropertyChanged(BR.user);
        this.user = user;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        notifyPropertyChanged(BR.password);
        this.password = password;
    }

    @Bindable
    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }
}
