package com.seproject.crowdfunder.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.seproject.crowdfunder.R;

public class EligibilityForStartRequest extends AppCompatActivity {


    CheckBox checkBox1, checkBox2;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahesh__elgibility);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        editText = findViewById(R.id.countryName);


        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().length() == 0)
                    Toast.makeText(EligibilityForStartRequest.this, "Country Name Invalid", Toast.LENGTH_SHORT).show();
                    checkBox1.setActivated(false);
            }
        });

        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().length() == 0)
                    Toast.makeText(EligibilityForStartRequest.this, "Country Name Invalid", Toast.LENGTH_SHORT).show();
                    checkBox2.setActivated(false);
            }
        });

    }

    public void goToFillDetail(View view) {
        if(editText.getText().toString().length() == 0)
            Toast.makeText(EligibilityForStartRequest.this, "Country Name Invalid", Toast.LENGTH_SHORT).show();
        else{

            if(checkBox2.isChecked() && checkBox1.isChecked()){
                startActivity(new Intent(this, FillDetailsPageForStartingReqest.class));
                finish();
            }
            else
                Toast.makeText(EligibilityForStartRequest.this, "Accept the requirements", Toast.LENGTH_SHORT).show();

        }

    }
}
