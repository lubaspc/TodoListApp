package com.lubinpc.todolist.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.lubinpc.retrofit.api.CBGeneric;
import com.lubinpc.retrofit.api.CBSuccess;
import com.lubinpc.todolist.R;
import com.lubinpc.todolist.databinding.ActivityLoginBinding;
import com.lubinpc.todolist.interactors.UserInteractor;
import com.lubinpc.todolist.models.UserVM;
import com.lubinpc.todolist.ui.notes.ListNotesActivity;

import mx.com.satoritech.womandriveandroidcliente.utils.Dialogs;
import mx.com.satoritech.womandriveandroidcliente.utils.ProgressDialog;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding vBind;
    private UserVM user = new UserVM();
    private UserHandle handle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handle = UserInteractor.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handle.getActive((user) -> {
            if (user != null){
                goToNext();
            }else{
                vBind = DataBindingUtil.setContentView(this, R.layout.activity_login);
                vBind.setUser(this.user);
                setupBtn();
            }
        });
    }

    private void setupBtn() {
        vBind.btnLogin.setOnClickListener(v -> {
            if (validate()){
                ProgressDialog progress = Dialogs.INSTANCE.progressDialog(getSupportFragmentManager());
                handle.login(user,(success,message) -> {
                    progress.dismiss();
                    if (!success) Dialogs.INSTANCE.alert(this,message);
                    else goToNext();

                });
            }
        });
        vBind.btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(this,RegisterActivity.class));
        });
    }
    private void goToNext(){
        startActivity(new Intent(this, ListNotesActivity.class));
        finish();
    }

    private boolean validate(){
        boolean isValid = true;
        vBind.tilUser.setError(null);
        vBind.tilPassword.setError(null);
        if (user.getUser() == null || user.getUser().trim().equals("")){
            vBind.tilUser.setError("Es necesario colocar un nombre de usuario");
            isValid = false;
        }

        if (user.getPassword() == null || user.getPassword().trim().equals("")){
            vBind.tilPassword.setError("Es necesario colocar una contraseña");
            isValid = false;
        }
        return isValid;
    }

    public interface UserHandle{
        void getActive(CBGeneric<UserVM> cb);
        void login(UserVM userVM, CBSuccess<String> cb);
    }
}