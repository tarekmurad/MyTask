package com.example.mytask;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mytask.Utils.SessionManager;

public class LoginViewModel extends AndroidViewModel implements ViewModelProvider.Factory {

    public MutableLiveData<String> Email = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();

    private MutableLiveData<LoginUser> userMutableLiveData;

    SessionManager sessionManager;
    private Context mContext;
    private Application mApplication;


    public LoginViewModel(@NonNull Application application, Context context) {
        super(application);
        mContext = context;
        mApplication = application;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new LoginViewModel(mApplication, mContext);
    }

    public MutableLiveData<LoginUser> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick(View view) {

        LoginUser loginUser = new LoginUser(Email.getValue(), Password.getValue());

        userMutableLiveData.setValue(loginUser);

        sessionManager = new SessionManager(getApplication().getApplicationContext());
        sessionManager.createSession(Email.getValue(), Password.getValue());


    }

    void openActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
    }


}