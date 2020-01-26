package com.example.mytask;

import android.util.Patterns;

public class LoginUser {

    private String Email;
    private String Password;

    public LoginUser(String EmailAddress, String Password) {
        Email = EmailAddress;
        this.Password = Password;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
    }

    public boolean isPasswordLengthGreaterThan5() {
        return getPassword().length() > 5;
    }

}