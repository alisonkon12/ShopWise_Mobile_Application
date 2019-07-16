package com.example.shopwise;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {

    public List<ShopList> shopListitem;


    Context context;

    public ShopListAdapter(List<ShopList> shopListitem, Context context) {
        this.shopListitem = shopListitem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.shopName.setText(shopListitem.get(position).getShopName());
        holder.shopLotNumber.setText(shopListitem.get(position).getShopLotNumber());
//        holder.date.setText(favouriteList.get(position).getCreatedDate());
        Picasso.with(context).load(shopListitem.get(position).getImageUrl()).into(holder.image);

        final String shop_item_id = shopListitem.get(position).shopListID;
        final String shopName = shopListitem.get(position).getShopName();
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Shop ID : " + shop_item_id, Toast.LENGTH_SHORT).show();
            }
        });

        holder.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(view.getContext(), MapActivity.class);
                intent.putExtra("shopName", shopName);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return shopListitem.size();
    }

    public void filterList(ArrayList<ShopList> filteredList) {
        shopListitem = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public TextView shopName, shopLotNumber;
        public ImageView image;
        public Button map;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            shopName = (TextView) mView.findViewById(R.id.shopName);
            shopLotNumber = (TextView) mView.findViewById(R.id.shopLotNumber);
            image = (ImageView) mView.findViewById(R.id.shopImage);
            map = (Button) mView.findViewById(R.id.button_map);
        }
    }
}
