package com.seproject.crowdfunder.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Utils.util;

public class ConfirmingPassword extends AppCompatActivity {

    private static final String TAG = "Confirming Password";
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirming_password);
        password = findViewById(R.id.editText);
    }

    public void ContinueClicked(View view) {
        String email =util.readFromSharedPreferencesString(this, util.SHARED_PREFERNCES_USER_DETAILS, util.SHARED_PREFERNCES_USER_DETAILS_EMAIL,0);


//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        assert email != null;
//        AuthCredential credential = EmailAuthProvider
//                .getCredential(email, password.getText().toString());
//
//        // Prompt the user to re-provide their sign-in credentials
//        assert user != null;
//        user.reauthenticate(credential)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Log.d(TAG, "User re-authenticated.");
//                        startActivity(new Intent(ConfirmingPassword.this, ResonForDeactivating.class));
//                        finish();
//                    }
//                });

        startActivity(new Intent(ConfirmingPassword.this, ResonForDeactivating.class));

//        if(password.getText().toString().matches("abcd"))
//            startActivity(new Intent(this, ResonForDeactivating.class));
//        else
//            Toast.makeText(this, "Wrong Password ", Toast.LENGTH_SHORT).show();
    }

    private void attemptLogin() {

        // Reset errors.
        password.setError(null);

        // Store values at the time of the login attempt.
        String email = password.getText().toString();

        boolean cancel = false;
        View focusView = null;



        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            password.setError(getString(R.string.error_field_required));
            focusView = password;
            cancel = true;
        } else if (!isEmailValid(email)) {
            password.setError(getString(R.string.error_invalid_email));
            focusView = password;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

        }
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
