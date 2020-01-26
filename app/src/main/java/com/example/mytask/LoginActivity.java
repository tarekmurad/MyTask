package com.example.mytask;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mytask.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginViewModel = ViewModelProviders.of(this, new LoginViewModel(this.getApplication(), this)).get(LoginViewModel.class);

        activityLoginBinding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        activityLoginBinding.setLifecycleOwner(this);
        activityLoginBinding.setLoginViewModel(loginViewModel);

        loginViewModel.getUser().observe(this, new Observer<LoginUser>() {
            @Override
            public void onChanged(@Nullable LoginUser loginUser) {

                if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getEmail())) {
                    activityLoginBinding.txtEmailAddress.setError("Enter an Email Address");
                    activityLoginBinding.txtEmailAddress.requestFocus();
                } else if (!loginUser.isEmailValid()) {
                    activityLoginBinding.txtEmailAddress.setError("Enter a Valid Email Address");
                    activityLoginBinding.txtEmailAddress.requestFocus();
                } else if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getPassword())) {
                    activityLoginBinding.txtPassword.setError("Enter a Password");
                    activityLoginBinding.txtPassword.requestFocus();
                } else if (!loginUser.isPasswordLengthGreaterThan5()) {
                    activityLoginBinding.txtPassword.setError("Enter at least 6 Digit password");
                    activityLoginBinding.txtPassword.requestFocus();
                } else {
                    loginViewModel.openActivity();
                }
            }
        });

    }
}