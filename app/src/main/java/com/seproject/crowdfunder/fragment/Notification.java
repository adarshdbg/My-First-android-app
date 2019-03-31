package com.seproject.crowdfunder.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seproject.crowdfunder.R;

import java.util.ArrayList;

public class Notification extends Fragment {

    DatabaseReference def;
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> re = new ArrayList<>();
    //ArrayList<pakage_noti> n=new ArrayList<>();
    ListView l;

    public Notification() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_dialog_profile, container, false);

//        l=(ListView) view.findViewById(R.id.list);
        //final ArrayAdapter<String> adapter=new ArrayAdapter<String>(guide_page.this,android.R.layout.simple_list_item_1,items);
        //l.setAdapter(adapter);
        def = FirebaseDatabase.getInstance().getReference("Notify_guide").child("noti");

//        def.addValueEventListener(new ValueEventListener() {
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot data:dataSnapshot.getChildren())
//                {
//                    String id=def.getKey();
//                    pakage_noti u=data.getValue(pakage_noti.class);
//
//                    n.add(new pakage_noti(u.name+" wants to buy this package from your village",u.village,u.notiid,u.uid));
//                }
//                //adapter.notifyDataSetChanged();
//
//            }


//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//        final mylist list=new mylist(view.getContext(),n);
//        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent1=new Intent(getActivity(),packages_2.class);
//                pakage_noti p=(pakage_noti) l.getItemAtPosition(position);
//                intent1.putExtra("Village",p.village);
//                intent1.putExtra("notid",p.notiid);
//                intent1.putExtra("uid",p.uid);
//                startActivity(intent1);
//            }
//        });
//        l.setAdapter(list);
//        return view;
//    }
        return view;
    }

}
