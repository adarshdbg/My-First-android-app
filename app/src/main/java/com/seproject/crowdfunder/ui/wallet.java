package com.seproject.crowdfunder.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.seproject.crowdfunder.R;

public class wallet extends AppCompatActivity {

    TextView balance;
    Button backtoapp;
    public TextView getBalance() {
        return balance;
    }

    public void setBalance(TextView balance) {
        this.balance = balance;
    }



    /* Get values from Intent */
//    Intent intent = getIntent();
//    String name  = intent.getStringExtra("KEY_StringName");
//    String name1  = intent.getStringExtra("KEY_StringName1");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        balance = findViewById(R.id.balance);
        backtoapp = findViewById(R.id.backtoapp);


//        balance.setText(name);

        backtoapp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent  = new Intent(wallet.this,MainActivity.class);
//                intent.putExtra("KEY_StringName", name);
//                intent.putExtra("KEY_StringName1", name1);
                startActivity(intent);
                // startActivity(new Intent(payment.this, wallet.class));

            }
        });

    }
}
