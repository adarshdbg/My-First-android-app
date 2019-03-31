package com.seproject.crowdfunder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PrivateRequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_private_request);
    }

    public void proceed(View view) {
        finish();
    }
}
