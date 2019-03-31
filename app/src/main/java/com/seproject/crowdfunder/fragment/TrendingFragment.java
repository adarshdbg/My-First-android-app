package com.seproject.crowdfunder.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class TrendingFragment extends Fragment {
    RecyclerView recyclerViewList;
    private List<RequestShortDetails> requestShortDetails = new ArrayList<>();
    RequestShortDetails requestShortDetailsFromdatabase;
    public static RequestShortDetailsAdapter adapter;
    public static ArrayList<Request> requests = new ArrayList<>();
    FirebaseDatabase database;
    int no_of_requests = 0;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        database = FirebaseDatabase.getInstance();
        prepareRequestList();

        recyclerViewList =  view.findViewById(R.id.list);
         adapter= new RequestShortDetailsAdapter(getContext(), requestShortDetails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewList.setLayoutManager(mLayoutManager);
        recyclerViewList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewList.setAdapter(adapter);


        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        return view;
    }

    public void prepareRequestList() {

        requestShortDetailsFromdatabase = new RequestShortDetails();
        final DatabaseReference myRef = database.getReference(util.path_base_path + util.path_requests);
        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                requestShortDetails.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                     Request request = postSnapshot.getValue(Request.class);
                     RequestShortDetails requestShortDetail= new RequestShortDetails(request.getRequest_id()+"",request.getTitle(), R.mipmap.ic_launcher_round, request.getUser_name(), "surathkal", 1, false, (int)request.getAmount_required(),request.getBackers(),request.getDays_left(), (int)(request.getAmount_funded()  *100 /request.getAmount_required()),request.getViews());
                     requestShortDetails.add(requestShortDetail);
                }
                requestShortDetails.sort(new Comparator<RequestShortDetails>() {
                    @Override
                    public int compare(RequestShortDetails o1, RequestShortDetails o2) {
                        if(o1.getViews() == o2.getViews())
                            return  0;
                        else if(o1.getViews() > o2.getViews())
                            return 1;
                        else
                            return -1;
                    }
                });
                adapter.notifyDataSetChanged();
                myRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

    }
}
