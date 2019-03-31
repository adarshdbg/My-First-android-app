package com.seproject.crowdfunder.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Utils.util;

public class RateUsActivity extends AppCompatActivity {

    RatingBar ratingbar;
    EditText comment;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);
        addListenerOnButtonClick();
    }

    public void addListenerOnButtonClick() {
        ratingbar = (RatingBar) findViewById(R.id.ratingBar);
        button = (Button) findViewById(R.id.button);
        comment = findViewById(R.id.comment);
        //Performing action on Button Click
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Getting the rating and displaying it on the toast
                String rating = String.valueOf(ratingbar.getRating());
                DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference(util.path_base_path + "rating/" + util.user.getUid() + "/");
                myRef1.child("comment").setValue(comment.getText().toString());
                myRef1.child("rate").setValue(rating);
                Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
            }

        });
    }
}
