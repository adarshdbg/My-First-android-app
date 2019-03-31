package com.seproject.crowdfunder.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.seproject.crowdfunder.R;

public class payment extends AppCompatActivity {

    public  Button send;
    String sendmoney;
    String name,name1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment2);

        sendmoney = findViewById(R.id.amount).toString();
        send = findViewById(R.id.sendButton);

        name= sendmoney;

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent  = new Intent(payment.this,wallet.class);
//                intent.putExtra("KEY_StringName", name);
//                intent.putExtra("KEY_StringName1", name1);
                startActivity(intent);
               // startActivity(new Intent(payment.this, wallet.class));

            }
        });

    }


}
