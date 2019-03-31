package com.seproject.crowdfunder.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.models.DonationDetail;
import com.seproject.crowdfunder.models.Request;
import com.seproject.crowdfunder.ui.DetailRequest;

import java.util.List;

public class nameAdapter extends RecyclerView.Adapter<nameAdapter.MyViewHolder> {

    private List<DonationDetail> list;
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


    public nameAdapter(List<DonationDetail> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DonationDetail donationDetail = list.get(position);
        holder.title.setText(donationDetail.getUid());
        //holder.amount.setText(request.getAmount());
        //holder.date.setText(request.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
