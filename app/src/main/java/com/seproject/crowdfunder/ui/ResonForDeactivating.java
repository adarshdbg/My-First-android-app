package com.seproject.crowdfunder.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Utils.util;

public class ResonForDeactivating extends AppCompatActivity {

    private static final String TAG = "ResonForDeactivating";
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reson_for_deactivating);

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //myRef = database.getReference("sub/user/Xjpxl1gzfFfpoAVUOjNdvdNKtCH3/is_deactivated");


    }

    public void deactivate_account(View view) {


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                            util.showMessage(ResonForDeactivating.this, "User account deleted");
                        }
                    }
                });

        //myRef.setValue("1");
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ResonForDeactivating.this, ActivityThanks.class));
        finish();
    }
}
