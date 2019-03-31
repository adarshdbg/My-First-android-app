package com.seproject.crowdfunder.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.models.Request;
import com.seproject.crowdfunder.ui.DetailRequest;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<Request> list;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, amount, date;

        public MyViewHolder(View view) {
            super(view);
            title =  view.findViewById(R.id.title);
            amount = view.findViewById(R.id.genre);
            date = view.findViewById(R.id.year);
        }
    }


    public HistoryAdapter(List<Request> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Request request = list.get(position);
        holder.title.setText(request.getTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DetailRequest.class).putExtra("rid",list.get(position).getRequest_id()));
            }
        });
        //holder.amount.setText(request.getAmount());
        //holder.date.setText(request.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
