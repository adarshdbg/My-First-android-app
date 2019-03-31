package com.seproject.crowdfunder.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.seproject.crowdfunder.R;

public class Splashscreen extends AppCompatActivity {


    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        mAuth = FirebaseAuth.getInstance();

        final Intent intentLoginRegister = new Intent(Splashscreen.this, StartActivity.class);
        final Intent intentMainActivity = new Intent(Splashscreen.this, MainActivity.class);


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if( currentUser == null)
                    startActivity(intentLoginRegister);
                else
                    startActivity(intentMainActivity);
                finish();
            }
        }, 500);


    }
}
