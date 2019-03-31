package com.seproject.crowdfunder.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.seproject.crowdfunder.R;

public class ContactUs extends AppCompatActivity {

    private EditText mEmailView;
    private EditText name;
    private EditText phone;
    private EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        mEmailView = findViewById(R.id.email);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        message = findViewById(R.id.message);

    }


    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        phone.setError(null);
        name.setError(null);
        message.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String ph = phone.getText().toString();
        String msg = message.getText().toString();
        String nm = name.getText().toString();

        boolean cancel = false;
        View focusView = null;



        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }else if(!isPhoneValid(ph)){
            phone.setError("Invalid Phone");
            focusView = phone;
            cancel = true;
        }
        else if(!isNameValid(nm)){
            name.setError("Invalid Name");
            focusView = phone;
            cancel = true;
        }
        else if(!isMsgValid(msg)){
            message.setError("Invalid Message");
            focusView = phone;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

        }
    }

    private boolean isMsgValid(String nm) {
        return nm.length() > 10;
    }

    private boolean isNameValid(String name) {
        return name.length() > 2;
    }

    private boolean isPhoneValid(String phone) {
        return phone.length() == 10;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 8;
    }

    public void asdf(View view) {
        attemptLogin();
    }
}
