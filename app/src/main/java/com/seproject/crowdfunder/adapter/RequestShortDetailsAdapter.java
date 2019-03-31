package com.seproject.crowdfunder.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Utils.util;
import com.seproject.crowdfunder.models.RequestShortDetails;
import com.seproject.crowdfunder.ui.Distance;

import java.util.List;
import java.util.Objects;

public class RequestShortDetailsAdapter extends RecyclerView.Adapter<RequestShortDetailsAdapter.MyViewHolder> {

    private List<RequestShortDetails> requestList;
    private Context context;
    FirebaseDatabase database;
    int no_bookmarks = 0;
    int no_views = 0;
    public static String  TAG = "RequestAdapter";
    public RequestShortDetailsAdapter(Context mContext, List<RequestShortDetails> requestList) {
        this.requestList = requestList;
        this.context = mContext;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, name, location, amountRequested, backers, timeLeft;
        ImageView profilePic,bookmarked;
        RatingBar rating;
        ProgressBar percentFunded;
        CardView cardView;

        MyViewHolder(View view){
            super(view);
            title =  view.findViewById(R.id.title);
            name =  view.findViewById(R.id.name);
            location =  view.findViewById(R.id.location);
            amountRequested =  view.findViewById(R.id.amount_requested);
            backers =  view.findViewById(R.id.backers);
            timeLeft =  view.findViewById(R.id.time_left);
            rating =  view.findViewById(R.id.rating);
            profilePic =  view.findViewById(R.id.profilePic);
            bookmarked =  view.findViewById(R.id.bookmark);
            percentFunded = view.findViewById(R.id.percent_funded);
            cardView = view.findViewById(R.id.card);

        }




    }

    @NonNull
    @Override
    public RequestShortDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_request, parent, false);

        return new RequestShortDetailsAdapter.MyViewHolder(itemView);
    }



    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final RequestShortDetails request = requestList.get(position);
        holder.title.setText(request.getTitle());
        holder.name.setText(request.getName());
        //holder.location.setText(request.getLocation());
        holder.profilePic.setImageResource(request.getProfilePic());
        holder.timeLeft.setText(String.format("%d\n days left", request.gettimeLeft()));
        holder.percentFunded.setProgress(request.getpercentFunded());
        holder.backers.setText(String.format("%d\ndonors", request.getBackers()));
        holder.amountRequested.setText(String.format("Rs.%d/-\n requested", request.getamountRequested()));
        holder.rating.setRating(request.getRating());
        if (request.isBookmarked()) {
            holder.bookmarked.setImageResource(R.drawable.ic_bookmarked);
        } else {
            holder.bookmarked.setImageResource(R.drawable.ic_not_bookmarked);
        }
        database = FirebaseDatabase.getInstance();


        final ImageView bookmark = holder.bookmarked;
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request.isBookmarked()){
                    bookmark.setImageResource(R.drawable.ic_not_bookmarked);
                    request.setBookmarked(false);
                    bookmark.setEnabled(false);
                    removeFromBookmarks(request.getId());
                    bookmark.setEnabled(true);
                }
                else {
                    bookmark.setImageResource(R.drawable.ic_bookmarked);
                    request.setBookmarked(true);
                    bookmark.setEnabled(false);
                    addToBookmarks(request.getId());
                    bookmark.setEnabled(true);
                }
            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //context.startActivity(new Intent(context, Distance.class));
                increaseViews(request.getId());
            }
        });

    }

    private void increaseViews(final String id) {

        final DatabaseReference myRef = database.getReference(util.path_base_path + util.path_requests + id );

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                no_views = Integer.parseInt(Objects.requireNonNull(dataSnapshot.child(util.path_views).getValue()).toString());

                DatabaseReference myRef1 = database.getReference(util.path_base_path + util.path_requests + id );
                myRef1.child("views").setValue(no_views+1);
                //Toast.makeText(context, "Bookmark Added", Toast.LENGTH_SHORT).show();

                myRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    private void removeFromBookmarks(final String id) {

        Log.d(TAG, "onDataChange: bookmark delete entered ");

        final DatabaseReference myRef1 = database.getReference(util.path_base_path + util.path_user + util.user.getUid() + "/" +util.path_bookmarks);
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: bookmark delete entered ");
                    if (Objects.requireNonNull(postSnapshot.getValue(String.class)).matches(id))
                        postSnapshot.getRef().removeValue();
                    Toast.makeText(context, "Bookmark Removed", Toast.LENGTH_SHORT).show();
                }
                myRef1.removeEventListener(this);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

    }

    private void addToBookmarks(final String id) {
        final DatabaseReference myRef = database.getReference(util.path_base_path + util.path_user + util.user.getUid() );

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            no_bookmarks = Integer.parseInt(Objects.requireNonNull(dataSnapshot.child(util.path_no_of_bookmarks).getValue()).toString());

                DatabaseReference myRef1 = database.getReference(util.path_base_path + util.path_user + util.user.getUid() + "/" + util.path_bookmarks + (no_bookmarks+1));
                myRef1.setValue(id);
                myRef1 = database.getReference(util.path_base_path + util.path_user + util.user.getUid() );
                myRef1.child(util.path_no_of_bookmarks).setValue(no_bookmarks+1);
                Toast.makeText(context, "Bookmark Added", Toast.LENGTH_SHORT).show();

            myRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }
}
