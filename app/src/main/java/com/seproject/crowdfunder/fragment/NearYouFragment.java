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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Utils.util;
import com.seproject.crowdfunder.adapter.RequestShortDetailsAdapter;
import com.seproject.crowdfunder.models.DistanceRequest;
import com.seproject.crowdfunder.models.Request;
import com.seproject.crowdfunder.models.RequestShortDetails;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.seproject.crowdfunder.ui.MainActivity.yourLocation;

public class NearYouFragment extends Fragment {
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



        return view;
    }

    public void prepareRequestList() {

        requestShortDetailsFromdatabase = new RequestShortDetails();
        final ArrayList<Request> list = new ArrayList<>();
        final DatabaseReference myRef = database.getReference(util.path_base_path + util.path_requests);
        final DatabaseReference myRef1 = database.getReference(util.path_base_path + util.path_user);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                requestShortDetails.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Request request = postSnapshot.getValue(Request.class);
                    RequestShortDetails requestShortDetail= new RequestShortDetails(request.getRequest_id()+"",request.getTitle(), R.mipmap.ic_launcher_round, request.getUser_name(), "surathkal", 1, false, (int)request.getAmount_required(),request.getBackers(),request.getDays_left(), (int)(request.getAmount_funded()  *100 /request.getAmount_required()),request.getViews());
                    requestShortDetails.add(requestShortDetail);
                }
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
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public void prepareRequestList() {
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference myRef = database.getReference(util.path_base_path + util.path_requests);
//
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//
//
//                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                    Double lat = Double.parseDouble(dataSnapshot1.child("lat").getValue().toString());
//                    Double lon = Double.parseDouble(dataSnapshot1.child("lon").getValue().toString());
//                    double dist = util.kilometerDistanceBetweenPoints(yourLocation.getLatitude(),yourLocation.getLongitude(),lat,lon);;
//                    util.distanceRequestArrayList.add(new DistanceRequest(dataSnapshot1.child("request_id").getValue().toString(),dist) );
//                }
//
//                myRef.removeEventListener(this);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//
//        util.distanceRequestArrayList.sort(new Comparator<DistanceRequest>() {
//            @Override
//            public int compare(DistanceRequest o1, DistanceRequest o2) {
//                if(o1.getDistance() == o2.getDistance())
//                    return 0;
//                else if(o1.getDistance() > o2.getDistance())
//                    return 1;
//                else
//                    return -1;
//            }
//        });
//
//        final DatabaseReference myRef1 = database.getReference(util.path_base_path + util.path_requests);
//        for (final DistanceRequest distanceRequest : util.distanceRequestArrayList) {
//
//            myRef1.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//
//                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                        if (dataSnapshot1.child("request_id").toString().matches(distanceRequest.getRequest_id()))
//                            util.requestsNearYou.add(dataSnapshot1.getValue(Request.class));
//
//                    }
//
//                    myRef.removeEventListener(this);
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError error) {
//                    // Failed to read value
//                    Log.w(TAG, "Failed to read value.", error.toException());
//                }
//            });
//        }
//
////        requestShortDetails.clear();
//        for (Request request : util.requestsNearYou){
//            requestShortDetails.add(new RequestShortDetails(request.getRequest_id()+"", request.getTitle(),R.mipmap.ic_launcher_round,request.getUser_name()
//                    ,"surathkal",1,false,(int)request.getAmount_required(),request.getBackers(),request.getDays_left(),(int)(request.getAmount_funded()*100/request.getAmount_required())));
//        }
//        adapter.notifyDataSetChanged();
//    }

