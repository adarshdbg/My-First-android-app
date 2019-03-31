package com.seproject.crowdfunder.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Utils.util;

public class ChooseProject extends AppCompatActivity {

    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asd);
        spinner = findViewById(R.id.spinner);
    }

    public void goToFillDetails(View view) {
        util.request.setType(spinner.getSelectedItem().toString());
        startActivity(new Intent(this, EligibilityForStartRequest.class));
        finish();
    }
}
