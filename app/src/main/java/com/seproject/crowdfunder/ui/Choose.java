package com.seproject.crowdfunder.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.seproject.crowdfunder.R;

public class Choose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
    }

    public void Goto(View view) {
        RadioButton radio1 = findViewById(R.id.radioProject);
        RadioButton radio2 = findViewById(R.id.radioStudy);
        if(radio1.isChecked()) {
            startActivity(new Intent(this, ChooseProject.class));
            finish();
        }
        if(radio2.isChecked()) {
            startActivity(new Intent(this, ChooseStudy.class));
            finish();
        }

    }
}
