package com.seproject.crowdfunder.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.seproject.crowdfunder.PrivateRequest;
import com.seproject.crowdfunder.R;

public class ChoosePublicPrivate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_public_private);
    }

    public void private_request(View view) {
        startActivity(new Intent(this, PrivateRequest.class));
        finish();
    }

    public void public_request(View view) {
        finish();
    }
}
