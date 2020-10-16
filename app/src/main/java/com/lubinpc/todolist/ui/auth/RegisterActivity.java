package com.lubinpc.todolist.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.lubinpc.retrofit.api.CBSuccess;
import com.lubinpc.todolist.R;
import com.lubinpc.todolist.databinding.ActivityRegisterBinding;
import com.lubinpc.todolist.interactors.UserInteractor;
import com.lubinpc.todolist.models.UserVM;
import com.lubinpc.todolist.utils.ToolbarUtils;

import mx.com.satoritech.womandriveandroidcliente.utils.Dialogs;
import mx.com.satoritech.womandriveandroidcliente.utils.ProgressDialog;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding vBind;
    private UserVM user = new UserVM();
    private UserHandle handle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handle = UserInteractor.getInstance();
        vBind = DataBindingUtil.setContentView(this, R.layout.activity_register);
        vBind.setUser(user);
        ToolbarUtils.INSTANCE.setupBackBtn(vBind.toolbar.toolbar,this,"");
        setupBtn();
    }

    private void setupBtn() {
        vBind.btnLogin.setOnClickListener(v -> {
            if (validate()){
                ProgressDialog progress = Dialogs.INSTANCE.progressDialog(getSupportFragmentManager());
                handle.register(user,(success,message) -> {
                    progress.dismiss();
                    if (!success) Dialogs.INSTANCE.alert(this,message);
                    else finish();
                });
            }
        });
    }

    private boolean validate(){
        boolean isValid = true;
        if (user.getUser().trim().equals("")){
            vBind.tilUser.setError("Es necesario colocar un nombre de usuario");
            isValid = false;
        }

        if (user.getPassword().trim().equals("")){
            vBind.tilUser.setError("Es necesario colocar una contraseña");
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public interface UserHandle{
        void register(UserVM userVM, CBSuccess<String> cb);
    }
}