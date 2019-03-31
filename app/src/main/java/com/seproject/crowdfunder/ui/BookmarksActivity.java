package com.seproject.crowdfunder.ui;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Utils.FirebaseMethods;
import com.seproject.crowdfunder.Utils.util;
import com.seproject.crowdfunder.adapter.RequestShortDetailsAdapter;
import com.seproject.crowdfunder.models.Request;
import com.seproject.crowdfunder.models.RequestShortDetails;

import java.util.ArrayList;
import java.util.List;

public class BookmarksActivity extends AppCompatActivity {

    private static final String TAG = "Bookmark";
    ArrayList<String> bookmarks = new ArrayList<>();

    RecyclerView recyclerViewList;
    private List<RequestShortDetails> requestShortDetails = new ArrayList<>();
    RequestShortDetails requestShortDetailsFromdatabase;
    RequestShortDetailsAdapter adapter;
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked);
        database = FirebaseDatabase.getInstance();
        bookmarks.clear();

        recyclerViewList =  findViewById(R.id.list);
        adapter= new RequestShortDetailsAdapter(this, requestShortDetails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewList.setLayoutManager(mLayoutManager);
        recyclerViewList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewList.setAdapter(adapter);


        myRef = database.getReference(util.path_base_path + util.path_user + util.user.getUid() + "/" + util.path_bookmarks);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        bookmarks.add(postSnapshot.getValue(String.class));
                }

                updateView();
                myRef.removeEventListener(this);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void updateView() {
        if(bookmarks.size() == 0)
            Toast.makeText(BookmarksActivity.this, "No Bookmarks", Toast.LENGTH_SHORT).show();
        else
        {   requestShortDetails.clear();
            int i =0;
            for( ; i<bookmarks.size();i++ ){
                myRef = database.getReference("sub/request/" + bookmarks.get(i));
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Request request = dataSnapshot.getValue(Request.class);

                            requestShortDetailsFromdatabase = new RequestShortDetails();
                            requestShortDetailsFromdatabase.setId(request.getUid());
                            requestShortDetailsFromdatabase.setName(request.getUser_name());
                            requestShortDetailsFromdatabase.setTitle(request.getTitle());
                            requestShortDetailsFromdatabase.setamountRequested((int)request.getAmount_required());
                            requestShortDetailsFromdatabase.setBackers(request.getBackers());
//                            requestShortDetailsFromdatabase.setLocation(dataSnapshot.child("/location").getValue().toString());
                            requestShortDetailsFromdatabase.setpercentFunded((int)(request.getAmount_funded()*100/request.getAmount_required()));
                            requestShortDetailsFromdatabase.setRating(1);
                            requestShortDetailsFromdatabase.settimeLeft(request.getDays_left());
                            requestShortDetailsFromdatabase.setProfilePic(R.drawable.app_icon);
                            requestShortDetailsFromdatabase.setBookmarked(true);

                            requestShortDetails.add(requestShortDetailsFromdatabase);
                            adapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        }

    }

    public void backClicked(View view) {
        super.onBackPressed();
    }
}
