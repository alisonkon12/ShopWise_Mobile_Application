package com.example.shopwise.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopwise.QRScanner;
import com.example.shopwise.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.xml.transform.Result;


public class RewardRecyclerAdapter extends RecyclerView.Adapter<RewardRecyclerAdapter.ViewHolder> {
    public List<RewardItem> reward_list;

    public Context context;


    public RewardRecyclerAdapter(List<RewardItem> reward_list, Context context) {
        this.reward_list = reward_list;
        this.context = context;
    }

    @NonNull
    @Override
    public RewardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RewardRecyclerAdapter.ViewHolder holder, int position) {

        holder.desc.setText(reward_list.get(position).getShopName());
        holder.detail.setText(reward_list.get(position).getRewardDetail());

        Picasso.with(context).load(reward_list.get(position).getImageAddress()).into(holder.image2);

        final String reward_item_id = reward_list.get(position).rewardItemId;
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, QRScanner.class);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return reward_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView desc;
        private TextView detail;
        private ImageView image2;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.item_cardview);
            mView = itemView;
            desc = mView.findViewById(R.id.item_desc);
            image2 = mView.findViewById(R.id.item_image);
            detail = mView.findViewById(R.id.item_detail);


        }


    }
}


